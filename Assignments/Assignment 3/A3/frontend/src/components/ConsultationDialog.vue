<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Schedule consultation" : "Update / Delete consultation" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="consultation.patientId" label="Patient Id" />
          <v-text-field v-model="consultation.doctorId" label="Doctor Id" />
          <v-text-field
            v-model="consultation.description"
            label="Description"
          />
          <v-text-field
            v-model="consultation.dateOfConsult"
            label="Consult Date"
          />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Schedule" : "Save" }}
          </v-btn>
          <v-btn @click="persist2">
            {{ toDelete ? "Delete" : "Reset Fields" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  name: "ConsultationDialog",
  props: {
    consultation: Object,
    opened: Boolean,
  },
  methods: {
    onHidden() {
      this.opened = false;
    },
    persist() {
      if (this.isNew) {
        api.consultations
          .create({
            patientId: this.consultation.patientId,
            doctorId: this.consultation.doctorId,
            description: this.consultation.description,
            dateOfConsult: this.consultation.dateOfConsult,
          })
          .then(() => this.$emit("refresh"));

        const socket = new SockJS("http://localhost:8088/gs-guide-websocket");
        const stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
          console.log("Connected: " + frame);
        });
        stompClient.send(
          "/app/checkIn",
          {},
          JSON.stringify({
            patientId: this.consultation.patientId,
            doctorId: this.consultation.doctorId,
            dateOfConsult: this.consultation.dateOfConsult,
          })
        );
        stompClient.disconnect();
      } else {
        api.consultations
          .update({
            id: this.consultation.id,
            patientId: this.consultation.patientId,
            doctorId: this.consultation.doctorId,
            description: this.consultation.description,
            dateOfConsult: this.consultation.dateOfConsult,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    persist2() {
      if (this.toDelete && !this.isNew) {
        api.consultations
          .delete(this.consultation.id)
          .then(() => this.$emit("refresh"));
      } else {
        this.consultation.description = "";
        this.consultation.dateOfConsult = null;
        this.consultation.patientId = null;
        this.consultation.doctorId = null;
      }
    },
  },
  computed: {
    isNew: function () {
      return !this.consultation.id;
    },
    toDelete: function () {
      return this.consultation.id;
    },
  },
};
</script>

<style scoped></style>
