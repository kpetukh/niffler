package niffler.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import niffler.model.Currency;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "currency", schema = "public", catalog = "niffler-currency")
public class CurrencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
//    @Enumerated(EnumType.STRING)
    private String currency;

    @Column(name = "currency_rate", nullable = false)
    private Double currencyRate;

}
