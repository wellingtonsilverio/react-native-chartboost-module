import { NativeModules, NativeEventEmitter } from "react-native";

const { ChartboostModule } = NativeModules;

const eventEmitter = new NativeEventEmitter(ChartboostModule);

const _subscriptions = new Map();

const addEventListener = (event, handler) => {
  const mappedEvent = event;
  if (mappedEvent) {
    let listener;
    if (event === "adFailedToLoad") {
      listener = eventEmitter.addListener(mappedEvent, (error) =>
        handler(createErrorFromErrorData(error))
      );
    } else {
      listener = eventEmitter.addListener(mappedEvent, handler);
    }
    _subscriptions.set(handler, listener);
    return {
      remove: () => removeEventListener(event, handler),
    };
  } else {
    // eslint-disable-next-line no-console
    console.warn(`Trying to subscribe to unknown event: "${event}"`);
    return {
      remove: () => {},
    };
  }
};

export default {
  ...ChartboostModule,
  addEventListener,
};
