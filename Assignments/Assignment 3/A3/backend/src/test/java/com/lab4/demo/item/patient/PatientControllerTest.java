package com.lab4.demo.item.patient;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.item.model.Patient;
import com.lab4.demo.item.model.dto.PatientDTO;
import com.lab4.demo.item.patient.PatientController;
import com.lab4.demo.item.patient.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.lab4.demo.TestCreationFactory.*;
import static com.lab4.demo.UrlMapping.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PatientControllerTest extends BaseControllerTest {

    @InjectMocks
    private PatientController controller;

    @Mock
    private PatientService itemService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new PatientController(itemService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allPatients() throws Exception {
        List<PatientDTO> items = TestCreationFactory.listOf(Patient.class);
        when(itemService.findAll()).thenReturn(items);

        ResultActions response = mockMvc.perform(get(PATIENTS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(items));

    }

    @Test
    void create() throws Exception {
        PatientDTO reqItem = PatientDTO.builder()
                .name(randomString())
                .identityCardNumber(randomInt())
                .address(randomString())
                .cnp(randomLong())
                .dateOfBirth(randomDate())
                .build();

        when(itemService.create(reqItem)).thenReturn(reqItem);

        ResultActions result = performPostWithRequestBody(PATIENTS, reqItem);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }

    @Test
    void changeName() throws Exception {
        long id = randomLong();
        String newName = randomString();
        PatientDTO reqPatient = PatientDTO.builder()
                .id(id)
                .name(newName)
                .identityCardNumber(randomInt())
                .address(randomString())
                .cnp(randomLong())
                .dateOfBirth(randomDate())
                .build();

        when(itemService.changeName(id, newName)).thenReturn(reqPatient);

        ResultActions result = performPatchWithRequestBodyAndPathVariable(PATIENTS + ENTITY, newName, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqPatient));
    }

    @Test
    void update() throws Exception {
        long id = randomLong();
        PatientDTO reqPatient = PatientDTO.builder()
                .id(id)
                .name(randomString())
                .identityCardNumber(randomInt())
                .address(randomString())
                .cnp(randomLong())
                .dateOfBirth(randomDate())
                .build();

        when(itemService.update(reqPatient)).thenReturn(reqPatient);

        ResultActions result = performPatchWithRequestBody(PATIENTS, reqPatient);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqPatient));
    }

    @Test
    void getPatient() throws Exception {
        long id = randomLong();
        PatientDTO reqItem = PatientDTO.builder()
                .id(id)
                .name(randomString())
                .identityCardNumber(randomInt())
                .address(randomString())
                .cnp(randomLong())
                .dateOfBirth(randomDate())
                .build();
        when(itemService.get(id)).thenReturn(reqItem);

        ResultActions result = performGetWithPathVariable(PATIENTS + ENTITY, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }

    @Test
    void delete() throws Exception {
        long id = randomLong();
        doNothing().when(itemService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(PATIENTS + ENTITY, id);
        verify(itemService, times(1)).delete(id);

        result.andExpect(status().isOk());

    }
}