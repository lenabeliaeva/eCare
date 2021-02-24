package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.dao.OptionDao;
import com.example.demo.exceptions.OptionsDependentException;
import com.example.demo.models.Contract;
import com.example.demo.models.Option;
import com.example.demo.models.Tariff;
import com.example.demo.services.OptionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class OptionServiceTest {

    @Mock
    private OptionDao optionDao;

    @InjectMocks
    private OptionServiceImpl optionService;

    private Option option;
    private Option anotherOption;
    private List<Option> options;
    private List<Option> notAddedToTariff;
    private Tariff tariff;
    private Contract contract;
    private Contract anotherContract;
    private Set<Option> contractOptions;

    @BeforeEach
    public void setup() {
        option = new Option();
        option.setId(1L);
        anotherOption = new Option();
        anotherOption.setId(2L);
        options = new LinkedList<>();
        options.add(option);
        options.add(anotherOption);

        tariff = new Tariff();
        tariff.add(anotherOption);

        notAddedToTariff = new LinkedList<>();
        notAddedToTariff.add(option);

        contract = new Contract();
        contract.add(option);
        contract.setTariff(tariff);

        contractOptions = new HashSet<>();
        contractOptions.add(option);
        contractOptions.add(anotherOption);

        anotherContract = new Contract();
    }

    @Test
    void shouldGetById() {
        when(optionDao.getById(1L)).thenReturn(option);
        Option found = optionService.getById(1L);
        assertEquals(option, found);
    }

    @Test
    void shouldNotGetById() {
        when(optionDao.getById(1L)).thenReturn(anotherOption);
        Option found = optionService.getById(1L);
        assertNotEquals(option, found);
    }

    @Test
    void shouldGetAllNotAddedToTariff() {
        when(optionDao.getAll()).thenReturn(options);
        List<Option> notAdded = optionService.getAllNotAddedToTariff(tariff);
        assertEquals(notAddedToTariff, notAdded);
    }

    @Test
    void shouldNotGetAllNotAddedToTariff() {
        when(optionDao.getAll()).thenReturn(new LinkedList<>());
        List<Option> notAdded = optionService.getAllNotAddedToTariff(tariff);
        assertNotEquals(notAddedToTariff, notAdded);
    }

    @Test
    void shouldGetContractOptions() {
        Set<Option> foundedContractOptions = optionService.getContractOptions(contract);
        assertEquals(contractOptions, foundedContractOptions);
    }

    @Test
    void shouldNotGetContractOptions() {
        Set<Option> foundedContractOptions = optionService.getContractOptions(anotherContract);
        assertNotEquals(contractOptions, foundedContractOptions);
    }

    @Test
    void shouldGetAllNotAddedToContractOptions() {
        when(optionDao.getAll()).thenReturn(options);
        List<Option> expected = new LinkedList<>(contractOptions);
        List<Option> found = optionService.getAllNotAddedToContractOptions(anotherContract);
        assertEquals(expected, found);
    }

    @Test
    void shouldNotGetAllNotAddedToContractOptions() {
        when(optionDao.getAll()).thenReturn(options);
        List<Option> expected = new LinkedList<>(contractOptions);
        List<Option> found = optionService.getAllNotAddedToContractOptions(contract);
        assertNotEquals(expected, found);
    }

    @Test
    void shouldGetIncompatibles() {
        when(optionDao.getById(1L)).thenReturn(option);
        option.addIncompatibleOption(anotherOption);
        Set<Option> foundIncompatibles = optionService.getIncompatibleOptions(option.getId());
        assertEquals(option.getIncompatibleOptions(), foundIncompatibles);
        option.deleteIncompatibleOption(anotherOption);
    }

    @Test
    void shouldGetCompatibles() {
        when(optionDao.getById(1L)).thenReturn(option);
        when(optionDao.getAll()).thenReturn(options);
        List<Option> compatibles = new LinkedList<>();
        compatibles.add(anotherOption);
        List<Option> foundCompatibles = optionService.getCompatible(option.getId());
        assertEquals(compatibles, foundCompatibles);
    }

    @Test
    void shouldNotGetCompatibles() {
        when(optionDao.getById(1L)).thenReturn(option);
        when(optionDao.getAll()).thenReturn(options);
        List<Option> compatibles = new LinkedList<>();
        compatibles.add(option);
        List<Option> foundCompatibles = optionService.getCompatible(option.getId());
        assertNotEquals(compatibles, foundCompatibles);
    }

    @Test
    void shouldGetDependent() {
        when(optionDao.getById(1L)).thenReturn(option);
        option.addDependentOption(anotherOption);
        Set<Option> expected = new HashSet<>();
        expected.add(anotherOption);
        Set<Option> found = optionService.getDependentOptions(1L);
        assertEquals(expected, found);
        option.deleteDependentOption(anotherOption);
    }
    
    @Test
    void shouldNotGetDependent() {
        when(optionDao.getById(1L)).thenReturn(option);
        Set<Option> unexpected = new HashSet<>();
        unexpected.add(option);
        Set<Option> found = optionService.getDependentOptions(1L);
        assertNotEquals(unexpected, found);
    }

    @Test
    void shouldMakeOptionsIncompatible() throws OptionsDependentException {
        when(optionDao.getById(1L)).thenReturn(option);
        when(optionDao.getById(2L)).thenReturn(anotherOption);
        doNothing().when(optionDao).update(isA(Option.class));
        Set<Option> expected = new HashSet<>();
        expected.add(anotherOption);
        optionService.addIncompatibleOption(option.getId(), anotherOption.getId());
        assertEquals(expected, option.getIncompatibleOptions());
    }

    @Test
    void shouldNotMakeOptionsIncompatible() throws OptionsDependentException {
        option.addDependentOption(anotherOption);
        when(optionDao.getById(1L)).thenReturn(option);
        when(optionDao.getById(2L)).thenReturn(anotherOption);
        assertThrows(OptionsDependentException.class, () -> optionService.addIncompatibleOption(option.getId(), anotherOption.getId()));
        option.deleteDependentOption(anotherOption);
    }
}
