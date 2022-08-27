package com.enigma.watatsea.events;

import com.enigma.waratsea.event.EventDispatcher;
import com.enigma.waratsea.event.EventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventDispatcherTest {
  private EventDispatcher<TestEvent> eventDispatcher;

  class Handler implements EventHandler<TestEvent> {

    @Override
    public void notify(TestEvent event) {
      var currentCount = event.getCount();
      event.setCount(++currentCount);

      eventDispatcher.unregister(this);
    }
  }

  @BeforeEach
  void setUpt() {
    eventDispatcher = new EventDispatcher<>("TestEvent");
  }

  @Test
  void testSingleHandlerEventDelivery() {
    eventDispatcher.register(this::testEventHandlerOne);

    var testEvent = new TestEvent(1);
    eventDispatcher.fire(testEvent);

    assertEquals(2, testEvent.getCount());
  }

  @Test
  void testMultipleHandlerEventDelivery() {
    eventDispatcher.register(this::testEventHandlerOne);
    eventDispatcher.register(this::testEventHandlerTwo);

    var testEvent = new TestEvent(1);
    eventDispatcher.fire(testEvent);

    assertEquals(3, testEvent.getCount());
  }

  @Test
  void testUnregisterOnEventDelivery() {
    eventDispatcher.register(new Handler());

    var testEvent = new TestEvent(1);
    eventDispatcher.fire(testEvent);

    assertEquals(2, testEvent.getCount());

    eventDispatcher.fire(testEvent);

    assertEquals(2, testEvent.getCount());
  }

  private void testEventHandlerOne(final TestEvent event) {
    var currentCount = event.getCount();
    event.setCount(++currentCount);
  }

  private void testEventHandlerTwo(final TestEvent event) {
    var currentCount = event.getCount();
    event.setCount(++currentCount);
  }

}
