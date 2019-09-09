
export default class ButtonBasics extends Component {
    _onPressButton() {
        alert('You tapped the button!')
    }

    render() {
        return (
            <View style={styles.container}>

                    <Button
                        onPress={this._onPressButton}
                        title="Press Me"
                        color="#841584"
                    />
            </View>
        );
    }
}