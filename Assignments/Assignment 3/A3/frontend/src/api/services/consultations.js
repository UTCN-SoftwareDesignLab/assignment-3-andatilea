import authHeader, { BASE_URL, HTTP } from "../http";
export default {
  allConsultations() {
    return HTTP.get(BASE_URL + "/consultations", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  create(consultation) {
    return HTTP.post(BASE_URL + "/consultations", consultation, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  update(consultation) {
    return HTTP.patch(BASE_URL + "/consultations", consultation, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(id) {
    return HTTP.delete(BASE_URL + "/consultations/" + id, {
      headers: authHeader(),
    }).then(() => {
      return true;
    });
  },
  updateDescription(consultation) {
    return HTTP.patch(BASE_URL + "/consultations/doctors", consultation, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
