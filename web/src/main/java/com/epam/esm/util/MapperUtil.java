package com.epam.esm.util;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.config.EnableHypermediaSupport;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The type Mapper util.
 */
@Configuration
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL_FORMS)
public class MapperUtil {

    /**
     * Gets mapper.
     *
     * @return the mapper
     */
    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }

    /**
     * Convert list page.
     *
     * @param <R>       the type parameter
     * @param <E>       the type parameter
     * @param list      the list
     * @param converter the converter
     * @return the page
     */
    public static <R, E> Page<R> convertList(Page<E> list, Function<E, R> converter) {
        return list.map(converter::apply);
    }

    /**
     * Convert list list.
     *
     * @param <R>       the type parameter
     * @param <E>       the type parameter
     * @param list      the list
     * @param converter the converter
     * @return the list
     */
    public static <R, E> List<R> convertList(List<E> list, Function<E, R> converter) {
        return list.stream().map(converter::apply).collect(Collectors.toList());
    }
}
