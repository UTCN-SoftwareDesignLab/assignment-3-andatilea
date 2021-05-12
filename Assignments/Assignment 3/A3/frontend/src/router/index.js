import Vue from "vue";
import VueRouter from "vue-router";
import UserList from "../views/UserList.vue";
import PatientList from "../views/PatientList.vue";
import ConsultationList from "../views/ConsultationList";
import DoctorConsultationList from "../views/DoctorConsultationList";

import { auth as store } from "../store/auth.module";
import Login from "../views/Login";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/users",
    name: "Users",
    component: UserList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin) {
        next();
      } else {
        next({ name: "DoctorConsultation" });
      }
    },
  },
  {
    path: "/doctors",
    name: "DoctorConsultation",
    component: DoctorConsultationList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isDoctor) {
        next();
      } else {
        next({ name: "Patients" });
      }
    },
  },
  {
    path: "/patients",
    name: "Patients",
    component: PatientList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isSecretary) {
        next();
      } else {
        next({ name: "Consultations" });
      }
    },
  },
  {
    path: "/consultations",
    name: "Consultations",
    component: ConsultationList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isSecretary) {
        next();
      } else {
        next({ name: "About" });
      }
    },
  },

  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue"),
  },
];

const router = new VueRouter({
  routes,
});

export default router;
