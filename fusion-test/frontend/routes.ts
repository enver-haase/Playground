import { Flow } from '@vaadin/flow-frontend';
import { Route } from '@vaadin/router';
import './views/hellotypescript/hello-typescript-view';
import './views/main-layout';

const { serverSideRoutes } = new Flow({
  imports: () => import('../target/frontend/generated-flow-imports'),
});

export type ViewRoute = Route & {
  title?: string;
  icon?: string;
  children?: ViewRoute[];
};

export const views: ViewRoute[] = [
  // place routes below (more info https://vaadin.com/docs/latest/fusion/routing/overview)
  {
    path: '',
    component: 'hello-typescript-view',
    icon: '',
    title: '',
  },
  {
    path: 'typescript',
    component: 'hello-typescript-view',
    icon: 'la la-globe',
    title: 'Hello Typescript',
  },
  {
    path: 'clickme',
    component: 'click-me-view',
    icon: 'la la-globe',
    title: 'Click ME',
    action: async (_context, _command) => {
      await import('./views/clickme/click-me-view');
      return;
    },
  },
];
export const routes: ViewRoute[] = [
  {
    path: '',
    component: 'main-layout',
    children: [
      ...views,
      // for server-side, the next magic line sends all unmatched routes:
      ...serverSideRoutes, // IMPORTANT: this must be the last entry in the array
    ],
  },
];
