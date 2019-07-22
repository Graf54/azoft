package my.test.azoft.repos;

import my.test.azoft.model.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

@Repository
public interface ExpensesRepo extends JpaRepository<Expenses, Integer> {
    @Query(value = "select (sum(EXPENSES.VALUE)) FROM EXPENSES where USER_ID = ?1 and DATE > ?2 and DATE < ?3", nativeQuery = true)
    BigDecimal getSumm(int userId, Date start, Date end);
}
