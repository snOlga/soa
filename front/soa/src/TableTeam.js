import React from 'react';
import { useState, useEffect } from 'react';
import "./App.css";

function TableTeam() {
    const [teams, setTeams] = useState([])
    const [editingTeam, setTeam] = useState({
        id: -1,
        name: "",
        humans: []
    });
    const [searchId, setSearchId] = useState({
        id: 0
    })

    const remove = (teamId) => {
        fetch("https://localhost:18018/teams/" + teamId, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" }
        })
    }

    const update = () => {
        fetch("https://localhost:18018/teams/" + editingTeam.id, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(editingTeam),
        })
        setTeam({ ...editingTeam, id: -1 })
    }

    const find = () => {
        fetch("https://localhost:18018/teams/" + searchId, {
            method: "GET",
            headers: { "Content-Type": "application/json" }
        }).then(resp => resp.json()).then(res => res.id != null ? setTeams([...teams, res]) : null)
    }

    const handleChange = (e) => {
        const { name, value } = e.target;
        setTeam((prev) => ({
            ...prev,
            [name]: name === "humans" ? [...editingTeam.humans] : value,
        }));
        if (name == "humans") {
            if (value == '')
                return
            let id = parseInt(value);
            if (editingTeam.humans.includes(id))
                return
            setTeam((prev) => ({
                ...prev,
                humans: [...editingTeam.humans, id]
            }));
        }
        console.log(editingTeam)
    };

    const removeHuman = (humanId) => {
        setTeam((prev) => ({
            ...prev,
            humans: editingTeam.humans.filter(id => id != humanId)
        }));
    }

    const showAllHumans = editingTeam.humans.map(
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

    const displayData = teams.length == 0 ? null : teams.map(
        (team) => {
            if (team.id == editingTeam.id) {
                return (
                    <tr>
                        <td>{team.id}</td>
                        <td>{team.name}</td>
                        <td>
                            {showAllHumans}
                            <input
                                name="humans"
                                type="number"
                                onChange={handleChange}
                                required={team.humans.length == 0}
                            />
                        </td>
                        <div className='icons'>
                            <div>
                                <button onClick={() => update()}>✔️</button>
                            </div>
                            <div>
                                <button onClick={() => setTeam({ ...editingTeam, id: -1 })}>❌</button>
                            </div>
                        </div>
                    </tr>
                )
            }
            else {
                return (
                    <tr>
                        <td>{team.id}</td>
                        <td>{team.name}</td>
                        <td>{team.humans.map(human => {
                            return (
                                <td>{human}</td>
                            )
                        })}</td>
                        <div className='icons'>
                            <div>
                                <button onClick={() => setTeam(team)}>✏️</button>
                            </div>
                            <div>
                                <button onClick={() => remove(team.id)}>🗑️</button>
                            </div>
                        </div>
                    </tr>
                )
            }
        }
    )


    return (
        <div className="form-container">
            <label>
                Get by id:
                <input
                    name="id"
                    type="number"
                    value={searchId.id}
                    onChange={setSearchId} />
                <button type="button" onClick={() => find()}>Find</button>
            </label>
            <table>
                <thead>
                    <tr>
                        <td>id</td>
                        <td>name</td>
                        <td>humans ids</td>
                    </tr>
                </thead>
                <tbody>
                    {displayData}
                </tbody>
            </table>
        </div>
    );
}


export default TableTeam;
