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
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="consultations"
      :search="search"
      @click:row="updateDescription"
    ></v-data-table>
    <DoctorConsultationDialog
      :opened="dialogVisible"
      :consultation="selectedConsultation"
      @refresh="refreshList"
    ></DoctorConsultationDialog>
  </v-card>
</template>

<script>
import api from "../api";
import DoctorConsultationDialog from "../components/DoctorConsultationDialog";

export default {
  name: "DoctorConsultationList",
  components: { DoctorConsultationDialog },
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
    updateDescription(consultation) {
      this.selectedConsultation = consultation;
      this.dialogVisible = true;
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
