package my.test.azoft.rest.controller;

import my.test.azoft.rest.exception.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RestController
//@RequestMapping("/api/expenses")
public class ExpensesController {
    private int count = 4;
    private List<Map<String, String>> expenes = new ArrayList<Map<String, String>>() {      //todo repalace
        {
            add(new HashMap<String, String>() {{
                put("id", "1");
                put("text", "one");
            }});
            add(new HashMap<String, String>() {{
                put("id", "2");
                put("text", "two");
            }});
            add(new HashMap<String, String>() {{
                put("id", "3");
                put("text", "three");
            }});
        }
    };

    @GetMapping
    public List<Map<String, String>> list() {
        return expenes;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return findOne(id);
    }

    private Map<String, String> findOne(String id) {
        return expenes.stream()
                .filter(expenes -> expenes.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> expenses){
        expenses.put("id", String.valueOf(count++));
        expenes.add(expenses);
        return expenses;
    }
    
    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> expenses){
        Map<String, String> expensesfromDb = findOne(id);
        expensesfromDb.putAll(expenses);
        return expensesfromDb;
    }



    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        expenes.remove(findOne(id));
    }
}
