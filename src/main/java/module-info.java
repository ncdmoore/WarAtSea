module com.enigma.waratsea {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.web;
  requires lombok;
  requires com.google.guice;
  requires com.google.guice.extensions.assistedinject;
  requires com.google.gson;
  requires org.slf4j;
  requires org.mapstruct;
  requires org.jetbrains.annotations;
  requires org.apache.commons.collections4;

  opens com.enigma.waratsea to javafx.fxml;

  opens com.enigma.waratsea.entity to com.google.gson;
  opens com.enigma.waratsea.model to com.google.gson;
  opens com.enigma.waratsea.model.player to com.google.gson;
  opens com.enigma.waratsea.model.aircraft to com.google.gson;
  opens com.enigma.waratsea.model.ship to com.google.gson;

  opens com.enigma.waratsea.mapper to com.google.guice;

  exports com.enigma.waratsea;
  exports com.enigma.waratsea.entity;
  exports com.enigma.waratsea.event;
  exports com.enigma.waratsea.exception;
  exports com.enigma.waratsea.mapper;
  exports com.enigma.waratsea.model;
  exports com.enigma.waratsea.model.aircraft;
  exports com.enigma.waratsea.model.squadron;
  exports com.enigma.waratsea.model.ship;
  exports com.enigma.waratsea.model.taskForce;
  exports com.enigma.waratsea.property;
  exports com.enigma.waratsea.orchestration;
  exports com.enigma.waratsea.repository;
  exports com.enigma.waratsea.repository.impl;
  exports com.enigma.waratsea.service;
  exports com.enigma.waratsea.service.impl;
  exports com.enigma.waratsea.strategy;
  exports com.enigma.waratsea.strategy.bombAlley;
  exports com.enigma.waratsea.strategy.arcticConvoy;

  exports com.enigma.waratsea.view;
  exports com.enigma.waratsea.view.resources;
  exports com.enigma.waratsea.view.pregame;

  exports com.enigma.waratsea.viewmodel;
  exports com.enigma.waratsea.viewmodel.pregame;
  exports com.enigma.waratsea.viewmodel.events;
  exports com.enigma.waratsea.model.player;
  opens com.enigma.waratsea.model.squadron to com.google.gson;
  exports com.enigma.waratsea.entity.ship;
  opens com.enigma.waratsea.entity.ship to com.google.gson;
  opens com.enigma.waratsea.entity.squadron to com.google.gson;
  exports com.enigma.waratsea.entity.squadron;
  opens com.enigma.waratsea.entity.aircraft to com.google.gson;
  exports com.enigma.waratsea.entity.aircraft;
}