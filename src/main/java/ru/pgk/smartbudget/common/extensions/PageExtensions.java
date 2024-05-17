package ru.pgk.smartbudget.common.extensions;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collection;
import java.util.List;

public class PageExtensions {

    public static <T> Page<T> toPage(
            Collection<T> elements,
            Integer offset,
            Integer limit
    ) {
        List<T> educationPeriods = elements.stream().skip((long) offset * limit).limit(limit).toList();
        PageRequest pageRequest = PageRequest.of(offset, limit);
        return new PageImpl<>(educationPeriods, pageRequest, elements.size());
    }
}

