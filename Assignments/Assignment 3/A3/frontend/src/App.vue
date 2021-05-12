<template>
  <v-app>
    <v-main>
      <v-container fluid>
        <TopBar v-if="isLoggedIn"></TopBar>
        <router-view></router-view>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import TopBar from "./components/TopBar";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import authHeader from "./api/http";
export default {
  name: "App",
  components: { TopBar },
  data: () => ({
    //
  }),
  computed: {
    isLoggedIn: function () {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  created: function () {
    const socket = new SockJS("http://localhost:8088/gs-guide-websocket");
    const stompClient = Stomp.over(socket);
    stompClient.connect(authHeader(), function (frame) {
      console.log("Connected : " + frame);
      stompClient.subscribe(
        "/topic/notification",
        function (appointmentReceived) {
          console.log(JSON.parse(appointmentReceived.body).content);
        }
      );
    });
  },
};
</script>
