export default function FetchElevatorFloorRequestService(addressedFloor){
    return fetch(`http://localhost:8080/rest/v1/elevators/request/${addressedFloor}`)
        .then(response => response.json())
        .catch((error) => {
            console.error(error);
        });
}