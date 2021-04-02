import { RouterLocation } from '@vaadin/router';
import { makeAutoObservable } from 'mobx';

export class AppStore {
  applicationName = 'LearningFusion';

  // The location, relative to the base path, e.g. "hello" when viewing "/hello"
  location = '';

  currentViewTitle = '';

  constructor() {
    makeAutoObservable(this);
  }

  setLocation(location: RouterLocation, serverSideRoute: boolean) {
    if (location.route && !serverSideRoute) {
      this.location = location.route.path;
    } else if (location.pathname.startsWith(location.baseUrl)) {
      this.location = location.pathname.substr(location.baseUrl.length);
    } else {
      this.location = location.pathname;
    }
    const route = location?.route as any;

    if (serverSideRoute) {
      this.currentViewTitle = document.title;
    } else {
      this.currentViewTitle = route?.title || '';
    }
  }
}
export const appStore = new AppStore();
