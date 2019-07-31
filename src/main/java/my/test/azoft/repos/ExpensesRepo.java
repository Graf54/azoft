package my.test.azoft.repos;

import my.test.azoft.model.Expenses;
import my.test.azoft.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Repository
public interface ExpensesRepo extends JpaRepository<Expenses, Integer> {
    @Query(value = "select (sum(EXPENSES.VALUE)) FROM EXPENSES where USER_ID = ?1 and DATE > ?2 and DATE < ?3", nativeQuery = true)
    Optional<BigDecimal> getSumm(int userId, Date start, Date end);

    @Query(value = "SELECT sum(EXPENSES.VALUE)/ count(distinct (CAST(DATE as DATE))) FROM EXPENSES WHERE USER_ID = ?1 AND DATE > ?2 AND DATE < ?3", nativeQuery = true)
    Optional<BigDecimal> getAverage(int userId, Date start, Date end);

    Page<Expenses> findAllByUser(User user, Pageable pageable);

    @Query(value = "SELECT * FROM EXPENSES WHERE USER_ID=?1 and cast(DATE as DATE) = CAST(?2 as DATE)",
            countQuery = "SELECT COUNT(*) FROM EXPENSES WHERE USER_ID=?1 and cast(DATE as DATE) = CAST(?2 as DATE)"
            , nativeQuery = true)
    Page<Expenses> findAllByUserAndDate(int userId, Date date, Pageable pageable);

    Optional<Expenses> findByIdAndUser(int id, User user);

    @Query(value = "SELECT MIN(EXPENSES.DATE) FROM EXPENSES WHERE USER_ID = ?1", nativeQuery = true)
    Optional<Date> getFirstDate(int userId);

    @Query(value = "SELECT MAX(EXPENSES.DATE) FROM EXPENSES WHERE USER_ID=?1", nativeQuery = true)
    Optional<Date> getLastDate(int userId);



}
