import { Flow } from '@vaadin/flow-frontend';
import { Route, RouterLocation } from '@vaadin/router';
import './views/helloworldfusion/hello-world-fusion-view';
import './views/main/main-view';

const { serverSideRoutes } = new Flow({
  imports: () => import('../target/frontend/generated-flow-imports'),
});

export type ViewRoute = Route & { title?: string; children?: ViewRoute[] };

export const views: ViewRoute[] = [
  // for client-side, place routes below (more info https://vaadin.com/docs/v19/flow/typescript/creating-routes.html)
  {
    path: '',
    component: 'mine-sweeper-view',
    title: 'Mine Sweeper',
    action: async () => {
      await import('./views/minesweeper/mine-sweeper-view');
    },
  },
  {
    path: 'hello-world-fusion',
    component: 'hello-world-fusion-view',
    title: 'Hello-World-Fusion',
  },
  {
    path: 'about-fusion',
    component: 'about-fusion-view',
    title: 'About-Fusion',
    action: async () => {
      await import('./views/aboutfusion/about-fusion-view');
    },
  },
  {
    path: 'master-detail-fusion',
    component: 'master-detail-fusion-view',
    title: 'Master-Detail-Fusion',
    action: async () => {
      await import('./views/masterdetailfusion/master-detail-fusion-view');
    },
  },
  {
    path: 'loan-fusion',
    component: 'loan-fusion-view',
    title: 'Loan-Fusion',
    action: async () => {
      await import('./views/loanfusion/loan-fusion-view');
    },
  },
  {
    path: 'mine-sweeper',
    component: 'mine-sweeper-view',
    title: 'Mine Sweeper',
    action: async () => {
      await import('./views/minesweeper/mine-sweeper-view');
    },
  },
];
export const routes: ViewRoute[] = [
  {
    path: '',
    component: 'main-view',
    children: [
      ...views,
      // for server-side, the next magic line sends all unmatched routes:
      ...serverSideRoutes, // IMPORTANT: this must be the last entry in the array
    ],
  },
];

export const isServerSideRoute = (location: RouterLocation): boolean => {
  return serverSideRoutes.includes(location.route as any);
};
