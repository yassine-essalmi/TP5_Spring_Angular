package ma.enset.tp5.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.enset.tp5.entities.PaymentType;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class NewPaymentDTO {

    private double amount;
    private PaymentType type;
    private LocalDate date;
    private String studentCode;
}
