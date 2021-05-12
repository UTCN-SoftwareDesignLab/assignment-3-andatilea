import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allUsers() {
    return HTTP.get(BASE_URL + "/users", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },

  create(user, role) {
    return HTTP.post(BASE_URL + "/users/" + role, user,{
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  update(user) {
    return HTTP.patch(BASE_URL + "/users", user, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  delete(id) {
    return HTTP.delete(BASE_URL + "/users/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
