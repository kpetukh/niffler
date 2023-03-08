package niffler.jupiter.extension;

import io.qameta.allure.AllureId;
import niffler.api.NifflerAuthClient;
import niffler.api.NifflerSpendClient;
import niffler.api.NifflerUserdataClient;
import niffler.config.Config;
import niffler.jupiter.annotation.*;
import niffler.model.CategoryJson;
import niffler.model.SpendJson;
import niffler.model.UserJson;
import niffler.utils.DateUtils;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import retrofit2.Response;

import java.util.*;

import static niffler.utils.DataUtils.generateRandomPassword;
import static niffler.utils.DataUtils.generateRandomUsername;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class CreateUserExtension implements BeforeEachCallback, ParameterResolver {

    private final NifflerAuthClient authClient = new NifflerAuthClient();
    private final NifflerUserdataClient userdataClient = new NifflerUserdataClient();
    private final NifflerSpendClient spendClient = new NifflerSpendClient();
    protected static final Config CFG = Config.getConfig();

    public static final ExtensionContext.Namespace
            ON_METHOD_USERS_NAMESPACE = ExtensionContext.Namespace.create(CreateUserExtension.class, Selector.METHOD),
            API_LOGIN_USERS_NAMESPACE = ExtensionContext.Namespace.create(CreateUserExtension.class, Selector.NESTED);

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        final String testId = getTestId(context);
        Map<Selector, GenerateUser> userAnnotations = extractGenerateUserAnnotations(context);
        for (Map.Entry<Selector, GenerateUser> entry : userAnnotations.entrySet()) {
            String username = entry.getValue().username();
            String password = entry.getValue().password();
            if ("".equals(username)) {
                username = generateRandomUsername();
            }
            if ("".equals(password)) {
                password = generateRandomPassword();
            }
            UserJson userJson = apiRegister(username, password);
            GenerateCategory[] categories = entry.getValue().categories();
            List<CategoryJson> createdCategories = new ArrayList<>();
            if (categories != null && categories.length > 0) {
                for (GenerateCategory category : categories) {
                    CategoryJson cj = new CategoryJson();
                    cj.setUsername(username);
                    cj.setCategory(category.value());
                    createdCategories.add(spendClient.createCategory(cj));
                }
            }
            userJson.setCategoryJsons(createdCategories);
            GenerateSpend[] spends = entry.getValue().spends();
            List<SpendJson> createdSpends = new ArrayList<>();
            if (spends != null) {
                for (GenerateSpend spend : spends) {
                    SpendJson sj = new SpendJson();
                    sj.setUsername(username);
                    sj.setSpendDate(!isEmpty(spend.spendDate()) ? DateUtils.fromString(spend.spendDate()) : new Date());
                    sj.setCategory(spend.category());
                    sj.setCurrency(spend.currency());
                    sj.setAmount(spend.amount());
                    sj.setDescription(spend.description());
                    createdSpends.add(spendClient.createSpend(sj));
                }
            }
            userJson.setSpendsJsons(createdSpends);
            context.getStore(entry.getKey().getNamespace()).put(testId, userJson);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserJson.class)
                && parameterContext.getParameter().isAnnotationPresent(User.class);
    }

    @Override
    public UserJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        final String testId = getTestId(extensionContext);
        User annotation = parameterContext.getParameter().getAnnotation(User.class);
        return extensionContext.getStore(annotation.selector().getNamespace()).get(testId, UserJson.class);
    }

    private String getTestId(ExtensionContext context) {
        return Objects.requireNonNull(
                context.getRequiredTestMethod().getAnnotation(AllureId.class)
        ).value();
    }

    public enum Selector {
        METHOD, NESTED;

        public ExtensionContext.Namespace getNamespace() {
            switch (this) {
                case METHOD -> {
                    return ON_METHOD_USERS_NAMESPACE;
                }
                case NESTED -> {
                    return API_LOGIN_USERS_NAMESPACE;
                }
                default -> {
                    throw new IllegalStateException();
                }
            }
        }
    }

    private Map<Selector, GenerateUser> extractGenerateUserAnnotations(ExtensionContext context) {
        Map<Selector, GenerateUser> annotationsOnTest = new HashMap<>();
        GenerateUser annotationOnMethod = context.getRequiredTestMethod().getAnnotation(GenerateUser.class);
        if (annotationOnMethod != null && annotationOnMethod.handleAnnotation()) {
            annotationsOnTest.put(Selector.METHOD, annotationOnMethod);
        }
        ApiLogin apiLoginAnnotation = context.getRequiredTestMethod().getAnnotation(ApiLogin.class);
        if (apiLoginAnnotation != null && apiLoginAnnotation.nifflerUser().handleAnnotation()) {
            annotationsOnTest.put(Selector.NESTED, apiLoginAnnotation.nifflerUser());
        }
        return annotationsOnTest;
    }

    private UserJson apiRegister(String username, String password) throws Exception {
        authClient.authorize();
        Response<Void> res = authClient.register(username, password);
        if (res.code() != 201) {
            throw new RuntimeException("User is not registered");
        }
        UserJson currentUser = userdataClient.getCurrentUser(username);
        currentUser.setPassword(password);
        return currentUser;
    }
}
