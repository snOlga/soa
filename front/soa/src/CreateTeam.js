import React, { useState } from "react";
import "./App.css";


function CreateTeam() {
    const [team, setTeam] = useState({
        name: "",
        humans: [1, 2]
    });


    const handleChange = (e) => {
        const { name, value } = e.target;
        setTeam((prev) => ({
            ...prev,
            [name]: name === "humans" ? [...team.humans] : value,
        }));
        if (name == "humans") {
            if (value == '')
                return
            let id = parseInt(value);
            if (team.humans.includes(id))
                return
            setTeam((prev) => ({
                ...prev,
                humans: [...team.humans, id]
            }));
        }
        console.log(team)
    };


    const handleSubmit = async (e) => {
        console.log(team)
        e.preventDefault();
        await fetch("https://localhost:18018/teams", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(team),
        });
    };


    const removeHuman = (humanId) => {
        setTeam((prev) => ({
            ...prev,
            humans: team.humans.filter(id => id != humanId)
        }));
    }


    const showAllHumans = team.humans.map(
        (humanId) => {
            return (
                <label>
                    Member:
                    <input
                        name="humans"
                        type="number"
                        value={humanId}
                        onChange={handleChange}
                    />
                    <button onClick={() => removeHuman(humanId)}>🗑️</button>
                </label>
            )
        })


    return (
        <div className="form-container">
            <h2>Create Team</h2>
            <form onSubmit={handleSubmit} className="form-fields">
                <label>
                    Name:
                    <input
                        name="name"
                        value={team.name}
                        onChange={handleChange}
                        required="true" />
                </label>


                {showAllHumans}
                <label>
                    Member:
                    <input
                        name="humans"
                        type="number"
                        onChange={handleChange}
                        required={team.humans.length == 0}
                    />
                </label>


                <button type="submit">Send</button>
            </form>
        </div>
    );
}


export default CreateTeam;





