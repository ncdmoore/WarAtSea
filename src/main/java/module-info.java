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
  requires jakarta.inject;

  opens com.enigma.waratsea.entity to com.google.gson;
  opens com.enigma.waratsea.model to com.google.gson;
  opens com.enigma.waratsea.model.player to com.google.gson;
  opens com.enigma.waratsea.model.aircraft to com.google.gson;
  opens com.enigma.waratsea.model.ship to com.google.gson;

  opens com.enigma.waratsea.mapper to com.google.guice;

  exports com.enigma.waratsea;
  exports com.enigma.waratsea.dto;
  exports com.enigma.waratsea.event;
  exports com.enigma.waratsea.event.action;
  exports com.enigma.waratsea.event.airfield;
  exports com.enigma.waratsea.event.phase;
  exports com.enigma.waratsea.event.port;
  exports com.enigma.waratsea.event.ship;
  exports com.enigma.waratsea.event.matcher;
  exports com.enigma.waratsea.event.squadron;
  exports com.enigma.waratsea.event.user;
  exports com.enigma.waratsea.exception;
  exports com.enigma.waratsea.mapper;

  exports com.enigma.waratsea.model;
  exports com.enigma.waratsea.model.airbase;
  exports com.enigma.waratsea.model.aircraft;
  exports com.enigma.waratsea.model.cargo;
  exports com.enigma.waratsea.model.die;
  exports com.enigma.waratsea.model.map;
  exports com.enigma.waratsea.model.mission;
  exports com.enigma.waratsea.model.option;
  exports com.enigma.waratsea.model.player;
  exports com.enigma.waratsea.model.port;
  exports com.enigma.waratsea.model.preferences;
  exports com.enigma.waratsea.model.release;
  exports com.enigma.waratsea.model.squadron;
  exports com.enigma.waratsea.model.ship;
  exports com.enigma.waratsea.model.statistics;
  exports com.enigma.waratsea.model.taskForce;
  exports com.enigma.waratsea.model.turn;
  exports com.enigma.waratsea.model.victory;
  exports com.enigma.waratsea.model.weather;

  exports com.enigma.waratsea.property;
  exports com.enigma.waratsea.orchestration;
  exports com.enigma.waratsea.repository;
  exports com.enigma.waratsea.repository.impl;
  exports com.enigma.waratsea.service;
  exports com.enigma.waratsea.service.impl;
  exports com.enigma.waratsea.strategy;
  exports com.enigma.waratsea.strategy.bombAlley;
  exports com.enigma.waratsea.strategy.arcticConvoy;

  exports com.enigma.waratsea.ai.strategy.commandStrategy;

  exports com.enigma.waratsea.view;
  exports com.enigma.waratsea.view.game;
  exports com.enigma.waratsea.view.pregame;
  exports com.enigma.waratsea.view.resources;

  exports com.enigma.waratsea.viewmodel;
  exports com.enigma.waratsea.viewmodel.game;
  exports com.enigma.waratsea.viewmodel.game.oob;
  exports com.enigma.waratsea.viewmodel.pregame;
  exports com.enigma.waratsea.viewmodel.events;
  exports com.enigma.waratsea.viewmodel.pregame.orchestration;

  exports com.enigma.waratsea.entity;
  exports com.enigma.waratsea.entity.aircraft;
  exports com.enigma.waratsea.entity.cargo;
  exports com.enigma.waratsea.entity.gson;
  exports com.enigma.waratsea.entity.matcher;
  exports com.enigma.waratsea.entity.mission;
  exports com.enigma.waratsea.entity.option;
  exports com.enigma.waratsea.entity.preferences;
  exports com.enigma.waratsea.entity.release;
  exports com.enigma.waratsea.entity.ship;
  exports com.enigma.waratsea.entity.squadron;
  exports com.enigma.waratsea.entity.taskforce;
  exports com.enigma.waratsea.entity.victory;



  opens com.enigma.waratsea.event.matcher to com.google.gson;

  opens com.enigma.waratsea.entity.aircraft to com.google.gson;
  opens com.enigma.waratsea.entity.cargo to com.google.gson;
  opens com.enigma.waratsea.entity.gson to com.google.gson;
  opens com.enigma.waratsea.entity.matcher to com.google.gson;
  opens com.enigma.waratsea.entity.mission to com.google.gson;
  opens com.enigma.waratsea.entity.option to com.google.gson;
  opens com.enigma.waratsea.entity.preferences to com.google.gson;
  opens com.enigma.waratsea.entity.release to com.google.gson;
  opens com.enigma.waratsea.entity.ship to com.google.gson;
  opens com.enigma.waratsea.entity.squadron to com.google.gson;
  opens com.enigma.waratsea.entity.taskforce to com.google.gson;
  opens com.enigma.waratsea.entity.victory to com.google.gson;

  opens com.enigma.waratsea.model.die to com.google.gson;
  opens com.enigma.waratsea.model.map to com.google.gson;
  opens com.enigma.waratsea.model.option to com.google.gson;
  opens com.enigma.waratsea.model.port to com.google.gson;
  opens com.enigma.waratsea.model.release to com.google.gson;
  opens com.enigma.waratsea.model.squadron to com.google.gson;
  opens com.enigma.waratsea.model.turn to com.google.gson;
  opens com.enigma.waratsea.model.weather to com.google.gson;
  opens com.enigma.waratsea.model.airbase to com.google.gson;

  opens com.enigma.waratsea.viewmodel to com.google.guice;
  opens com.enigma.waratsea.viewmodel.game.oob to com.google.guice;

  exports com.enigma.waratsea.view.game.oob;
  exports com.enigma.waratsea.view.game.ship;
  exports com.enigma.waratsea.view.game.squadron;
  exports com.enigma.waratsea.view.dialog;
  exports com.enigma.waratsea.repository.provider;
  exports com.enigma.waratsea.repository.serializer;
}