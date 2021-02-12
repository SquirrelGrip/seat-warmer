import './App.css';
import React, {useEffect, useState} from "react";
import axios from "axios";

function DisplayAvallable(props) {
  return (
    <div>{props.available}/{props.maximum} at {props.date}</div>
  );
}

function Reserve(props) {
  const handleClick = () => props.onReserve(props.name);
  return (
    <div>
      <button onClick={handleClick}>Reserve</button><br/>
      <span>{props.reservation}</span>
    </div>
  );
}

function EditName(props) {
  const handleChange = (event) => props.onChangeName(event);
  return (
    <input type="text" value={props.name} onChange={handleChange}/>
  );
}

function App() {
  const [available, setAvailable] = useState(0);
  const [maximum, setMaximum] = useState(0);
  const [name, setName] = useState("");
  const [reservation, setReservation] = useState("None");
  const date = new Date();

  const dayOfMonth = date.getDate()
  const month = date.getMonth() + 1
  const year = date.getFullYear()
  const dateString = dayOfMonth + '/' + month + '/' + year;
  const statusUrl = 'http://localhost:8080/api/v1/status/' + year + '/' + month + '/' + dayOfMonth;
  const reservationUrl = 'http://localhost:8080/api/v1/reservation/' + year + '/' + month + '/' + dayOfMonth + '?name=';

  const updateStatus = () => {
    const fetchStatus = async () => {
      const result = await axios(
        statusUrl
      );
      setAvailable(result.data.available);
      setMaximum(result.data.maximum);
    };
    fetchStatus();
  };

  useEffect(() => updateStatus());

  const changeName = (event) => {
    setName(event.target.value);
  };

  const reserve = (name) => {
    console.log(name);
    axios.post(reservationUrl + name, {})
      .then(function () {
        setReservation("Successful")
      })
      .catch(function () {
        setReservation("Fully Booked")
      })
      .finally(function () {
        updateStatus();
      });
  };

  return (
    <div>
      <EditName onChangeName={changeName} name={name}/>
      <DisplayAvallable available={available} maximum={maximum} date={dateString}/>
      <Reserve name={name} onReserve={reserve} reservation={reservation}/>
    </div>
  );
}

export default App;
