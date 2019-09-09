
export default function FetchElevatorsService() {
    return fetch('http://localhost:8080/rest/v1/elevators')
        .then((response) => response.json())
        .catch((error) => {
            console.error(error);
        });
}