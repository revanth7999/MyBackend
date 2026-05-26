// Ro run - k6 run test.js

import http from "k6/http";

const token =
  "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIkFETUlOIl0sImlhdCI6MTc3OTc5MTAwMSwiZXhwIjoxNzc5NzkxOTAxfQ.5tfE5O2kP6-E1VxNAvm2T2_BoHUFolMA2EXQLxUnRzY";

export const options = {
    vus: 20, // Virtual users
    duration: "30s",
};

export default function () {
  http.post(
    "http://localhost:8080/api/restaurants/create",

    JSON.stringify({
      name: "Test-k6-2",
      cuisine: "Indian",
    }),

    {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    }
  );
}