import { NativeModules, NativeEventEmitter } from "react-native";

const { ChartboostModule } = NativeModules;

const eventEmitter = new NativeEventEmitter(ChartboostModule);

const _subscriptions = new Map();

const addEventListener = (event, handler) => {
  const mappedEvent = event;

  let listener;

  listener = eventEmitter.addListener(mappedEvent, handler);

  _subscriptions.set(handler, listener);

  return {
    remove: () => removeEventListener(event, handler),
  };
};

const removeEventListener = (type, handler) => {
  const listener = _subscriptions.get(handler);
  if (!listener) {
    return;
  }
  listener.remove();
  _subscriptions.delete(handler);
};

export default {
  ...ChartboostModule,
  addEventListener,
};
