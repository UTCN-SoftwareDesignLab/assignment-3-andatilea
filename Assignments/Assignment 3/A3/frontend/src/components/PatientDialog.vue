<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create patient" : "Update / Delete patient" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="patient.name" label="Name" />
          <v-text-field v-model="patient.identityCardNumber" label="Identity Card Nb" />
          <v-text-field v-model="patient.cnp" label="CNP" />
          <v-text-field v-model="patient.dateOfBirth" label="Birth Date" />
          <v-text-field v-model="patient.address" label="Address" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
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

export default {
  name: "PatientDialog",
  props: {
    patient: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.patients
          .create({
            name: this.patient.name,
            identityCardNumber: this.patient.identityCardNumber,
            cnp: this.patient.cnp,
            dateOfBirth: this.patient.dateOfBirth,
            address: this.patient.address
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.patients
          .update({
            id: this.patient.id,
            name: this.patient.name,
            identityCardNumber: this.patient.identityCardNumber,
            cnp: this.patient.cnp,
            dateOfBirth: this.patient.dateOfBirth,
            address: this.patient.address
          })
          .then(() => this.$emit("refresh"));
      }
    },
    persist2() {
      if (this.toDelete && !this.isNew) {
        api.patients.delete(this.patient.id).then(() => this.$emit("refresh"));
      } else {
        this.patient.name = "";
        this.patient.identityCardNumber = null;
        this.patient.cnp = null;
        this.patient.dateOfBirth = null;
        this.patient.address = "";
      }
    },
  },
  computed: {
    isNew: function () {
      return !this.patient.id;
    },

    toDelete: function () {
      return this.patient.id;
    },
  },
};
</script>

<style scoped></style>
