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
    const [searchId, setSearchId] = useState(0)

    const remove = (teamId) => {
        fetch("https://localhost:18081/teams/" + teamId, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" }
        })
    }

    const update = () => {
        fetch("https://localhost:18081/teams/" + editingTeam.id, {
            method: "GET",
            headers: { "Content-Type": "application/json" }
        }).then(resp => resp.json()).then(res => res.id != null ? setTeams([res]) : null)
        setTeam({ ...editingTeam, id: -1 })
    }

    const find = () => {
        fetch("https://localhost:18081/teams/" + searchId, {
            method: "GET",
            headers: { "Content-Type": "application/json" }
        }).then(resp => resp.json()).then(res => res.id != null ? setTeams([res]) : null)
    }

    const handleChange = (e) => {
        const { name, value } = e.target;
        addHuman(value);
    };

    const addHuman = (humanId) => {
        fetch("https://localhost:18081/teams/" + editingTeam.id + "/members/" + humanId, {
            method: "POST",
            headers: { "Content-Type": "application/json" }
        }).then(resp => resp.json()).then(res => res.id != null ? setTeams([res]) : null)
        setTeam((prev) => ({
            ...prev,
            humans: editingTeam.humans.filter(id => id != humanId)
        }));
    }

    const removeHuman = (humanId) => {
        fetch("https://localhost:18018/teams/" + editingTeam.id + "/members/" + humanId, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" }
        }).then(resp => resp.json()).then(res => res.id != null ? setTeams([res]) : null)
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
                        readOnly
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
                    value={searchId}
                    onChange={(e) => setSearchId(e.target.value)}
                />
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
