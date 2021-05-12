<template>
  <v-card>
    <v-card-title>
      Consultations
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addConsultation">Add Consultation</v-btn>
      <v-btn primary large block @click="attemptChangeView">Switch View</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="consultations"
      :search="search"
      @click:row="updateConsultation"
    ></v-data-table>
    <ConsultationDialog
      :opened="dialogVisible"
      :consultation="selectedConsultation"
      @refresh="refreshList"
    ></ConsultationDialog>
  </v-card>
</template>

<script>
import api from "../api";
import router from "../router";
import ConsultationDialog from "../components/ConsultationDialog";

export default {
  name: "ConsultationList",
  components: { ConsultationDialog },
  data() {
    return {
      consultations: [],
      search: "",
      headers: [
        {
          text: "Patient Id",
          align: "start",
          sortable: false,
          value: "patientId",
        },
        { text: "Doctor Id", value: "doctorId" },
        { text: "Consultation Date", value: "dateOfConsult" },
        { text: "Description", value: "description" },
      ],
      dialogVisible: false,
      selectedConsultation: {},
    };
  },
  methods: {
    updateConsultation(consultation) {
      this.selectedConsultation = consultation;
      this.dialogVisible = true;
    },
    addConsultation() {
      this.selectedConsultation = {};
      this.dialogVisible = true;
    },
    attemptChangeView() {
      router.push("/patients");
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedConsultation = {};
      this.consultations = await api.consultations.allConsultations();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
