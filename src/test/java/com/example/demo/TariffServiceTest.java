package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.dao.TariffDao;
import com.example.demo.dto.TariffDto;
import com.example.demo.models.Tariff;
import com.example.demo.services.TariffServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TariffServiceTest {

    @Mock
    private TariffDao tariffDao;

    @InjectMocks
    private TariffServiceImpl tariffService;

    private Tariff tariff;
    private Tariff anotherTariff;
    private List<Tariff> tariffs;
    private List<Tariff> notAddedToContractTariffs;

    @BeforeEach
    public void setup() {
        tariff = new Tariff();
        tariff.setId(1L);
        anotherTariff = new Tariff();
        anotherTariff.setId(2L);
        tariffs = new LinkedList<>();
        tariffs.add(anotherTariff);
        tariffs.add(tariff);
        notAddedToContractTariffs = new LinkedList<>();
        notAddedToContractTariffs.add(anotherTariff);
    }

    @Test
    void shouldGetAllTariffs() {
        when(tariffDao.getAll()).thenReturn(new LinkedList<>());
        tariffService.getAll();
        verify(tariffDao).getAll();
    }

    @Test
    void shouldGetById() {
        when(tariffDao.getById(1L)).thenReturn(tariff);
        Tariff founded = tariffService.getById(1L);
        assertEquals(founded, tariff);
    }

    @Test
    void shouldNotGetById() {
        when(tariffDao.getById(1L)).thenReturn(anotherTariff);
        Tariff founded = tariffService.getById(1L);
        assertNotEquals(founded, tariff);
    }

    @Test
    void shouldGetNotAddedToContractTariffs() {
        when(tariffDao.getAll()).thenReturn(tariffs);
        List<Tariff> notContractTariffs = tariffService.getNotAddedToContractTariffs(tariff.getId());
        assertEquals(notContractTariffs, notAddedToContractTariffs);
    }

    @Test
    void shouldNotGetNotAddedToContractTariffs() {
        when(tariffDao.getAll()).thenReturn(tariffs);
        List<Tariff> notContractTariffs = tariffService.getNotAddedToContractTariffs(anotherTariff.getId());
        assertNotEquals(notContractTariffs, notAddedToContractTariffs);
    }
}
