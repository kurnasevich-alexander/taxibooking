package eu.senla.taxibooking.api.service;

import org.springframework.data.domain.Page;

import java.util.List;

public interface UtilService {

    <S, T> List<T> mapList(List<S> source, Class<T> targetClass);
    <S, T> List<T> mapPage(Page<S> source, Class<T> targetClass);
}
