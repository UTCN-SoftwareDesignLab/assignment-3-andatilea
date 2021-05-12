<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-form>
          <v-text-field
            v-model="toUpdate"
            label="Description"
          />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">Update description</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "DoctorConsultationDialog",
  props: {
    consultation: Object,
    opened: Boolean,
    toUpdate:String
  },
  methods: {
    persist() {
      api.consultations
        .updateDescription({
          id: this.consultation.id,
          description: this.toUpdate,
        })
        .then(() => this.$emit("refresh"));
    },
  },
};
</script>

<style scoped></style>
