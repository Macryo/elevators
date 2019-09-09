import React from 'react';
import {ActivityIndicator, Button, FlatList, StyleSheet, Text, TextInput, View} from "react-native";
import fetchAllElevatorsService from "../services/fetchAllElevatorsService";
import fetchElevatorFloorRequestService from "../services/fetchElevatorFloorRequestService";
import fetchElevatorReleaseService from "../services/fetchElevatorReleaseService";

export default class ElevatorView extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            elevatorsList: [],
            isElevatorListLoading: true,
            //
            elevatorToBeCalled: {
                addressedFloor: 0,
                id: 0,
                direction: 'NONE',
                busy: false
            },
            isElevatorToBeCalledLoading: true,
            //
            isElevatorReleaseLoading: true,
            elevatorToBeReleaseId: 1
        }

    }

    componentDidMount() {
        this.loadElevatorsList();
    }

    render() {
        return (
            <View>
                <View>
                    <Text value={"Available elevators:"}/>
                    {
                        this.renderElevatorsList()
                    }
                </View>
                <View>
                    {
                        this.renderElevatorFloorRequest()
                    }
                </View>
                <View>
                    {
                        this.renderElevatorRelease()
                    }
                </View>
            </View>
        );
    }

    renderElevatorsList() {
        if (this.state.isElevatorListLoading) {
            return (
                <View style={styles.container}>
                    <ActivityIndicator/>
                </View>
            )
        }

        return (
            <View style={styles.container}>
                <Text style={styles.text}>Current list of Elevators</Text>
                <Button title={"Refresh list"} onPress={this.loadElevatorsList}/>
                <FlatList
                    data={this.state.elevatorsList}
                    renderItem={({item}) => <Text style={styles.text}>Elevator{item.id}, On floor: {item.addressedFloor}</Text>}
                    keyExtractor={({id}) => id.toString()}
                />
            </View>
        );
    }

    loadElevatorsList = () => {
        fetchAllElevatorsService().then(data => {
            this.setState({
                elevatorsList: data,
                isElevatorListLoading: false
            });
        });
    };

    renderElevatorFloorRequest() {
        if (this.state.isElevatorToBeCalledLoading) {
            return (
                <View style={styles.container}>
                    <TextInput
                        maxLength={3}
                        placeholder={"To floor..."}
                        keyboardType={"numeric"}
                        onChangeText={(text) => {
                            this.setState({
                                elevatorToBeCalled: {
                                    isElevatorToBeCalledLoading: true,
                                    addressedFloor: parseInt(text)
                                }
                            })
                        }
                        }/>
                    <Button style={styles.button} title={"Call Elevator"} onPress={this.callElevator}/>
                </View>
            );
        }
        return (
            <View style={styles.container}>
                <View>
                    <TextInput
                        maxLength={3}
                        placeholder={"To floor..."}
                        keyboardType={"numeric"}
                        onChangeText={(text) => {
                            this.setState({
                                isElevatorToBeCalledLoading: true,
                                elevatorToBeCalled: {
                                    addressedFloor: parseInt(text)
                                }
                            });
                        }
                        }/>
                    <Button style={styles.button} title={"Call Elevator"} onPress={this.callElevator}/>
                    {
                        this.state.isElevatorToBeCalledLoading ? <Text style={styles.text}>{this.state.elevatorToBeCalled.id} is going to
                            floor {this.state.elevatorToBeCalled.addressedFloor}</Text> : null
                    }
                </View>
            </View>
        );
    }

    callElevator = () => {
        fetchElevatorFloorRequestService(this.state.elevatorToBeCalled.addressedFloor).then(data => {
            this.setState({
                isElevatorToBeCalledLoading: false,
                elevatorToBeCalled: {
                    addressedFloor: data.addressedFloor,
                    id: data.id,
                    direction: data.direction,
                    busy: data.busy
                },
            });
        });
        this.componentDidMount();
    };

    renderElevatorRelease() {
        return (
            <View style={styles.container}>
                <TextInput
                    maxLength={3}
                    placeholder={"Elevator id..."}
                    keyboardType={"numeric"}
                    onChangeText={(text) => {
                        this.setState({
                            elevatorToBeReleaseId: parseInt(text)
                        });
                    }
                    }/>
                <Button style={styles.button} title={"Release Elevator"} onPress={this.releaseElevator}/>
                {
                    this.state.isElevatorReleaseLoading ? null : <Text style={styles.text}>Elevator released.</Text>
                }
            </View>
        );
    }

    releaseElevator = () => {
        fetchElevatorReleaseService(this.state.elevatorToBeReleaseId).then(data => {
            this.setState({
                isElevatorReleaseLoading: false,
            });
        });
    }
}

const styles = StyleSheet.create({
    container: {
        alignContent: 'center',
        justifyContent: 'center',
        margin: 30,
        flex: 1
    },
    button: {
        textAlign: 'center',
        marginLeft: 30,
        marginRight: 30
    },
    text: {
        textAlign: 'center',
        color: 'blue',
        fontWeight: 'bold',
    }
});