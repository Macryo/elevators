export default function FetchElevatorFloorRequestService(elevatorId) {
    return fetch(`http://localhost:8080/rest/v1/elevators/release/${elevatorId}`)
        .catch((error) => {
            console.error(error);
        });
}