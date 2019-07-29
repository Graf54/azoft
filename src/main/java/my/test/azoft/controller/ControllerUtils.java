package my.test.azoft.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtils {
    static Map<String, String> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );
        return bindingResult.getFieldErrors().stream().collect(collector);
    }

    public static String redirect(RedirectAttributes redirectAttributes, String referer, String urlEmpty) {
        if (referer == null || referer.isEmpty()) {
            return urlEmpty;//"redirect:/expenses";
        }
        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();

        components.getQueryParams()
                .forEach((key, value) -> {
                    if (!redirectAttributes.containsAttribute(key)) { // если уже содержит, то добавлять не нужно
                        redirectAttributes.addAttribute(key, value);
                    }
                });

        return "redirect:" + components.getPath();
    }
}
