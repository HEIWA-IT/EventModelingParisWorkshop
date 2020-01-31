class App extends React.Component {

  state = {
    "rooms": []
  }

  constructor(props) {
    super(props);
    this.getRoomsToClean();
  }

  render() {
    return (
      <div>
        {this.state.rooms.map(room => <div key={room}>Room {room} <StartCleaningButton roomId={room} /></div>)}
      </div>
    )
  }

  getRoomsToClean = () => {

    fetch("http://localhost:8080/xtra-cleaning/room", {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    })
      .then(httpResult => httpResult.json())
      .then(jsonData => {
        console.log("getRoomsToClean", jsonData);
        this.setState(jsonData);
      })
      .catch(error => console.log("something wrong happened: ", error));
  }

}

class StartCleaningButton extends React.Component {

  state = {
    started: false
  };

  render() {

    if (this.state.started) {
      return 'Cleaning started';
    }

    return (
      <button onClick={() => this.startCleaning(this.props.roomId)}>
        Start Cleaning
      </button>
    );
  }

  startCleaning = (roomId) => {

    this.setState({ started: true });

    fetch("http://localhost:8080/xtra-cleaning/room/" + roomId + "/start", {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      }
    })
      .catch(error => console.log("something wrong happened: ", error));
  }

}

ReactDOM.render(<App />, document.getElementById('root'))