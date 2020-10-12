import axios from "axios";
import proxy from "../proxy/proxy-settings";

class AuthorizationService {
login (username, password) {
  return proxy
    .post("http://localhost:8080/authenticate", {
      username,
      password,
    })
    .then((response) => {
      if (response.data.accessToken) {
        localStorage.clear();
        localStorage.setItem("user", JSON.stringify(response.data));
      }

      return response.data;
    });
};

  reg(username, password, role) {
    return proxy
    .post("http://localhost:8080/registration", {
      username,
      password,
      role
    });
  }


getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"));
};

}

export default new AuthorizationService();