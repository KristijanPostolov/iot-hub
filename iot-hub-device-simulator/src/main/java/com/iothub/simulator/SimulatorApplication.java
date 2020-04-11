package com.iothub.simulator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.iothub.messages.ConfigMessage;
import com.iothub.messages.ParameterConfig;
import com.iothub.messages.ParameterType;
import com.iothub.messages.StateMessage;
import com.iothub.simulator.service.DeviceState;
import com.iothub.simulator.service.MessagePublisher;
import com.iothub.simulator.service.PushStateListener;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@EnableConfigurationProperties
@RequiredArgsConstructor
public class SimulatorApplication implements CommandLineRunner {

  private final DeviceState deviceState;
  private final PushStateListener pushStateListener;
  private final MqttClient mqttClient;
  private final MessagePublisher messagePublisher;

  public static void main(String[] args) {
    SpringApplication.run(SimulatorApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    System.out.println("Welcome to the Device simulator\n\n\n");
    System.out.println("Enter the device key: ");

    String key = br.readLine();
    deviceState.setKey(key);
    mqttClient.subscribe("devices/" + key + "/push", (topic, message) ->
        pushStateListener.handlePushStateMessage(topic, message.getPayload()));

    while (true) {
      System.out.println("Enter the new message that the device should send: ");
      System.out.println("To send config message enter: config;<parameter>,<type>,<actuator>;<parameter>,<type>,<actuator>...");
      System.out.println("To send state message enter: state;<parameter>,<value>;<parameter>,<value>...");
      System.out.println("To exit simulator enter: exit");

      String command = br.readLine();
      String[] parts = command.split(";");
      if ("config".equals(parts[0])) {
        sendConfigMessage(parts);
        System.out.println("Config message was sent successfully");
      } else if ("state".equals(parts[0])) {
        sendStateMessage(parts);
        System.out.println("State message was sent successfully");
      } else if ("exit".equals(parts[0])) {
        break;
      } else {
        System.out.println("Invalid command");
      }
    }
    System.out.println("Exiting simulator");
  }

  private void sendConfigMessage(String[] parts) {
    Map<String, ParameterConfig> config = new HashMap<>();
    Map<String, String> types = new HashMap<>();
    for (int i = 1; i < parts.length; i++) {
      String[] p = parts[i].split(",");
      ParameterConfig parameterConfig = new ParameterConfig(ParameterType.valueOf(p[1]), "true".equals(p[2]));
      config.put(p[0], parameterConfig);
      types.put(p[0], p[1]);
    }
    deviceState.setTypes(types);
    ConfigMessage configMessage = new ConfigMessage(config);
    messagePublisher.sendConfigMessage(configMessage);
  }

  private void sendStateMessage(String[] parts) {
    Map<String, String> values = new HashMap<>();
    for (int i = 1; i < parts.length; i++) {
      String[] p = parts[i].split(",");
      deviceState.getValues().put(p[0], p[1]);
      values.put(p[0], p[1]);
    }

    StateMessage stateMessage = new StateMessage(Instant.now(), values);
    messagePublisher.sendStateMessage(stateMessage);
  }
}
