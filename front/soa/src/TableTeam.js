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

    const update = (teamId) => {
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
                    <svg onClick={() => removeHuman(humanId)} xmlns="http://www.w3.org/2000/svg" viewBox="0 0 25 25" width="30px"><defs></defs><g id="trash"><path class="cls-1" d="M20.5,4H16.86l-.69-2.06A1.37,1.37,0,0,0,14.87,1H10.13a1.37,1.37,0,0,0-1.3.94L8.14,4H4.5a.5.5,0,0,0,0,1h.34l1,17.59A1.45,1.45,0,0,0,7.2,24H17.8a1.45,1.45,0,0,0,1.41-1.41L20.16,5h.34a.5.5,0,0,0,0-1ZM9.77,2.26A.38.38,0,0,1,10.13,2h4.74a.38.38,0,0,1,.36.26L15.81,4H9.19Zm8.44,20.27a.45.45,0,0,1-.41.47H7.2a.45.45,0,0,1-.41-.47L5.84,5H19.16Z" /><path class="cls-1" d="M9.5,10a.5.5,0,0,0-.5.5v7a.5.5,0,0,0,1,0v-7A.5.5,0,0,0,9.5,10Z" /><path class="cls-1" d="M12.5,9a.5.5,0,0,0-.5.5v9a.5.5,0,0,0,1,0v-9A.5.5,0,0,0,12.5,9Z" /><path class="cls-1" d="M15.5,10a.5.5,0,0,0-.5.5v7a.5.5,0,0,0,1,0v-7A.5.5,0,0,0,15.5,10Z" /></g></svg>
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
                                <svg onClick={() => update(team)} xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"><polygon points="9.993 19.421 3.286 12.58 4.714 11.179 10.007 16.579 19.293 7.293 20.707 8.707 9.993 19.421" /></svg>
                            </div>
                            <div>
                                <svg onClick={() => setTeam({ ...editingTeam, id: -1 })} xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"><title>5.Cancel</title><g id="_5.Cancel" data-name="5.Cancel"><path d="M12,24A12,12,0,1,1,24,12,12.013,12.013,0,0,1,12,24ZM12,2A10,10,0,1,0,22,12,10.011,10.011,0,0,0,12,2Z" /><rect x="11" y="6.343" width="2" height="11.314" transform="translate(-4.971 12) rotate(-45)" /><rect x="6.343" y="11" width="11.314" height="2" transform="translate(-4.971 12) rotate(-45)" /></g></svg>
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
                                <svg onClick={() => setTeam(team)} xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="30px"><path d="M20.7,5.537a1.024,1.024,0,0,1,0,1.448L8.527,19.158,3,21l1.842-5.527L17.015,3.3a1.024,1.024,0,0,1,1.448,0Z" /></svg>
                            </div>
                            <div>
                                <svg onClick={() => remove(team.id)} xmlns="http://www.w3.org/2000/svg" viewBox="0 0 25 25" width="30px"><defs></defs><g id="trash"><path class="cls-1" d="M20.5,4H16.86l-.69-2.06A1.37,1.37,0,0,0,14.87,1H10.13a1.37,1.37,0,0,0-1.3.94L8.14,4H4.5a.5.5,0,0,0,0,1h.34l1,17.59A1.45,1.45,0,0,0,7.2,24H17.8a1.45,1.45,0,0,0,1.41-1.41L20.16,5h.34a.5.5,0,0,0,0-1ZM9.77,2.26A.38.38,0,0,1,10.13,2h4.74a.38.38,0,0,1,.36.26L15.81,4H9.19Zm8.44,20.27a.45.45,0,0,1-.41.47H7.2a.45.45,0,0,1-.41-.47L5.84,5H19.16Z" /><path class="cls-1" d="M9.5,10a.5.5,0,0,0-.5.5v7a.5.5,0,0,0,1,0v-7A.5.5,0,0,0,9.5,10Z" /><path class="cls-1" d="M12.5,9a.5.5,0,0,0-.5.5v9a.5.5,0,0,0,1,0v-9A.5.5,0,0,0,12.5,9Z" /><path class="cls-1" d="M15.5,10a.5.5,0,0,0-.5.5v7a.5.5,0,0,0,1,0v-7A.5.5,0,0,0,15.5,10Z" /></g></svg>
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
