package io.github.mat3e.jhipster.taskr.cucumber.stepdefs;

import io.github.mat3e.jhipster.taskr.TaskApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = TaskApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
