package ma.enset.tp5.repository;

import ma.enset.tp5.entities.Payment;
import ma.enset.tp5.entities.PaymentStatus;
import ma.enset.tp5.entities.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findByStudentCode(String code);
    List<Payment> findByStatus(PaymentStatus status);
    List<Payment> findByType(PaymentType type);
}
