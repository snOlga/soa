import React from 'react';
import { useState } from 'react';
import "./App.css";


var demo = [
    {
        id: 1,
        name: "string",
        coordinates: {
            x: -636,
            y: -128
        },
        isRealHero: "true",
        impactSpeed: 300,
        soundtrackName: "string",
        weaponType: "AXE",
        mood: "SORROW",
        car: {
            name: "string",
            coolness: 0
        }
    },
    {
        id: 2,
        name: "string",
        coordinates: {
            x: -636,
            y: -128
        },
        isRealHero: "true",
        impactSpeed: 300,
        soundtrackName: "string",
        weaponType: "AXE",
        mood: "SORROW",
        car: {
            name: "string",
            coolness: 0
        }
    },
    {
        id: 3,
        name: "string",
        coordinates: {
            x: -636,
            y: -128
        },
        isRealHero: "true",
        impactSpeed: 300,
        soundtrackName: "string",
        weaponType: "AXE",
        mood: "SORROW",
        car: {
            name: "string",
            coolness: 0
        }
    },
    {
        id: 4,
        name: "string",
        coordinates: {
            x: -636,
            y: -128
        },
        isRealHero: "true",
        impactSpeed: 300,
        soundtrackName: "string",
        weaponType: "AXE",
        mood: "SORROW",
        car: {
            name: "string",
            coolness: 0
        }
    },
    {
        id: 1,
        name: "string",
        coordinates: {
            x: -636,
            y: -128
        },
        isRealHero: "true",
        impactSpeed: 300,
        soundtrackName: "string",
        weaponType: "AXE",
        mood: "SORROW",
        car: {
            name: "string",
            coolness: 0
        }
    },
    {
        id: 1,
        name: "string",
        coordinates: {
            x: -636,
            y: -128
        },
        isRealHero: "true",
        impactSpeed: 300,
        soundtrackName: "string",
        weaponType: "AXE",
        mood: "SORROW",
        car: {
            name: "string",
            coolness: 0
        }
    },
    {
        id: 1,
        name: "string",
        coordinates: {
            x: -636,
            y: -128
        },
        isRealHero: "true",
        impactSpeed: 300,
        soundtrackName: "string",
        weaponType: "AXE",
        mood: "SORROW",
        car: {
            name: "string",
            coolness: 0
        }
    },
    {
        id: 1,
        name: "string",
        coordinates: {
            x: -636,
            y: -128
        },
        isRealHero: "true",
        impactSpeed: 300,
        soundtrackName: "string",
        weaponType: "AXE",
        mood: "SORROW",
        car: {
            name: "string",
            coolness: 0
        }
    },
    {
        id: 1,
        name: "string",
        coordinates: {
            x: -636,
            y: -128
        },
        isRealHero: "true",
        impactSpeed: 300,
        soundtrackName: "string",
        weaponType: "AXE",
        mood: "SORROW",
        car: {
            name: "string",
            coolness: 0
        }
    },
    {
        id: 1,
        name: "string",
        coordinates: {
            x: -636,
            y: -128
        },
        isRealHero: "true",
        impactSpeed: 300,
        soundtrackName: "string",
        weaponType: "AXE",
        mood: "SORROW",
        car: {
            name: "string",
            coolness: 0
        }
    },
    {
        id: 1,
        name: "string",
        coordinates: {
            x: -636,
            y: -128
        },
        isRealHero: "true",
        impactSpeed: 300,
        soundtrackName: "string",
        weaponType: "AXE",
        mood: "SORROW",
        car: {
            name: "string",
            coolness: 0
        }
    },
    {
        id: 1,
        name: "string",
        coordinates: {
            x: -636,
            y: -128
        },
        isRealHero: "true",
        impactSpeed: 300,
        soundtrackName: "string",
        weaponType: "AXE",
        mood: "SORROW",
        car: {
            name: "string",
            coolness: 0
        }
    },
    {
        id: 1,
        name: "string",
        coordinates: {
            x: -636,
            y: -128
        },
        isRealHero: "true",
        impactSpeed: 300,
        soundtrackName: "string",
        weaponType: "AXE",
        mood: "SORROW",
        car: {
            name: "string",
            coolness: 0
        }
    },
    {
        id: 1,
        name: "string",
        coordinates: {
            x: -636,
            y: -128
        },
        isRealHero: "true",
        impactSpeed: 300,
        soundtrackName: "string",
        weaponType: "AXE",
        mood: "SORROW",
        car: {
            name: "string",
            coolness: 0
        }
    },
    {
        id: 1,
        name: "string",
        coordinates: {
            x: -636,
            y: -128
        },
        isRealHero: "true",
        impactSpeed: 300,
        soundtrackName: "string",
        weaponType: "AXE",
        mood: "SORROW",
        car: {
            name: "string",
            coolness: 0
        }
    }
]


function TableHuman() {
    const [editingHuman, setHuman] = useState({
        id: -1,
        name: "",
        coordinates: { x: 0, y: 0 },
        isRealHero: false,
        impactSpeed: 0,
        soundtrackName: "",
        weaponType: "AXE",
        mood: "SORROW",
        car: { name: "", coolness: 0 },
    })

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setHuman((prev) => ({
            ...prev,
            [name]: type === "checkbox" ? checked : value,
        }));
    };


    const handleNestedChange = (e, group) => {
        const { name, value } = e.target;
        setHuman((prev) => ({
            ...prev,
            [group]: {
                ...prev[group],
                [name]: value,
            },
        }));
    };

    const weaponTypes = ["AXE", "KNIFE", "MACHINE_GUN", "BAT"];
    const moods = ["SORROW", "LONGING", "GLOOM", "APATHY", "FRENZY"];

    const remove = (humanId) => {

    }

    const update = (human) => {

        setHuman({ ...human, id: -1 })
    }

    const displayData = demo.map(
        (human) => {
            if (human.id == editingHuman.id) {
                return (
                    <tr>
                        <td>{human.id}</td>
                        <td>
                            <input
                                name="name"
                                value={human.name}
                                onChange={handleChange}
                                required="true" />
                        </td>
                        <td>{new Date(human.creationDate).toDateString()}</td>
                        <td>
                            <input
                                type="checkbox"
                                name="isRealHero"
                                checked={human.isRealHero}
                                onChange={handleChange}
                            />
                            Real hero
                        </td>
                        <td>
                            <input
                                name="soundtrackName"
                                value={human.soundtrackName}
                                onChange={handleChange}
                            />
                        </td>
                        <td>
                            <input
                                name="impactSpeed"
                                type="number"
                                value={human.impactSpeed}
                                onChange={handleChange}
                                required="true"
                            />
                        </td>
                        <td>
                            <select
                                name="weaponType"
                                value={human.weaponType}
                                onChange={handleChange}>
                                {weaponTypes.map((w) => (
                                    <option key={w} value={w}>
                                        {w}
                                    </option>
                                ))}
                            </select>
                        </td>
                        <td>
                            <select name="mood" value={human.mood} onChange={handleChange}>
                                {moods.map((m) => (
                                    <option key={m} value={m}>
                                        {m}
                                    </option>
                                ))}
                            </select>
                        </td>
                        <td><table>
                            <thead>
                                <tr>
                                    <td>name</td>
                                    <td>coolness</td>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>
                                        <input
                                            name="name"
                                            value={human.car.name}
                                            onChange={(e) => handleNestedChange(e, "car")}
                                        />
                                    </td>
                                    <td>
                                        <input
                                            name="coolness"
                                            type="number"
                                            value={human.car.coolness}
                                            onChange={(e) => handleNestedChange(e, "car")}
                                        />
                                    </td>
                                </tr>
                            </tbody>
                        </table></td>
                        <td><table>
                            <thead>
                                <tr>
                                    <td>x</td>
                                    <td>y</td>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>
                                        <input
                                            name="x"
                                            type="number"
                                            value={human.coordinates.x}
                                            onChange={(e) => handleNestedChange(e, "coordinates")}
                                            required="true"
                                        />
                                    </td>
                                    <td>
                                        <input
                                            name="y"
                                            type="number"
                                            value={human.coordinates.y}
                                            onChange={(e) => handleNestedChange(e, "coordinates")}
                                            required="true"
                                        />
                                    </td>
                                </tr>
                            </tbody>
                        </table></td>
                        <div className='icons'>
                            <div>
                                <svg onClick={() => update(human)} xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"><polygon points="9.993 19.421 3.286 12.58 4.714 11.179 10.007 16.579 19.293 7.293 20.707 8.707 9.993 19.421" /></svg>
                            </div>
                            <div>
                                <svg onClick={() => setHuman({ ...human, id: -1 })} xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"><title>5.Cancel</title><g id="_5.Cancel" data-name="5.Cancel"><path d="M12,24A12,12,0,1,1,24,12,12.013,12.013,0,0,1,12,24ZM12,2A10,10,0,1,0,22,12,10.011,10.011,0,0,0,12,2Z" /><rect x="11" y="6.343" width="2" height="11.314" transform="translate(-4.971 12) rotate(-45)" /><rect x="6.343" y="11" width="11.314" height="2" transform="translate(-4.971 12) rotate(-45)" /></g></svg>
                            </div>
                        </div>
                    </tr>
                )
            }
            else {
                return (
                    <tr>
                        <td>{human.id}</td>
                        <td>{human.name}</td>
                        <td>{new Date(human.creationDate).toDateString()}</td>
                        <td>{human.isRealHero}</td>
                        <td>{human.soundtrackName}</td>
                        <td>{human.impactSpeed}</td>
                        <td>{human.weaponType}</td>
                        <td>{human.mood}</td>
                        <td><table>
                            <thead>
                                <tr>
                                    <td>name</td>
                                    <td>coolness</td>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>
                                        {human.car.name}
                                    </td>
                                    <td>
                                        {human.car.coolness}
                                    </td>
                                </tr>
                            </tbody>
                        </table></td>
                        <td><table>
                            <thead>
                                <tr>
                                    <td>x</td>
                                    <td>y</td>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>
                                        {human.coordinates.x}
                                    </td>
                                    <td>
                                        {human.coordinates.y}
                                    </td>
                                </tr>
                            </tbody>
                        </table></td>
                        <div className='icons'>
                            <div>
                                <svg onClick={() => setHuman(human)} xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="30px"><path d="M20.7,5.537a1.024,1.024,0,0,1,0,1.448L8.527,19.158,3,21l1.842-5.527L17.015,3.3a1.024,1.024,0,0,1,1.448,0Z" /></svg>
                            </div>
                            <div>
                                <svg onClick={() => remove(human.id)} xmlns="http://www.w3.org/2000/svg" viewBox="0 0 25 25" width="30px"><defs></defs><g id="trash"><path class="cls-1" d="M20.5,4H16.86l-.69-2.06A1.37,1.37,0,0,0,14.87,1H10.13a1.37,1.37,0,0,0-1.3.94L8.14,4H4.5a.5.5,0,0,0,0,1h.34l1,17.59A1.45,1.45,0,0,0,7.2,24H17.8a1.45,1.45,0,0,0,1.41-1.41L20.16,5h.34a.5.5,0,0,0,0-1ZM9.77,2.26A.38.38,0,0,1,10.13,2h4.74a.38.38,0,0,1,.36.26L15.81,4H9.19Zm8.44,20.27a.45.45,0,0,1-.41.47H7.2a.45.45,0,0,1-.41-.47L5.84,5H19.16Z" /><path class="cls-1" d="M9.5,10a.5.5,0,0,0-.5.5v7a.5.5,0,0,0,1,0v-7A.5.5,0,0,0,9.5,10Z" /><path class="cls-1" d="M12.5,9a.5.5,0,0,0-.5.5v9a.5.5,0,0,0,1,0v-9A.5.5,0,0,0,12.5,9Z" /><path class="cls-1" d="M15.5,10a.5.5,0,0,0-.5.5v7a.5.5,0,0,0,1,0v-7A.5.5,0,0,0,15.5,10Z" /></g></svg>
                            </div>
                        </div>
                    </tr>
                )
            }
        }
    )


    return (
        <div className="form-container">
            <table>
                <thead>
                    <tr>
                        <td>id</td>
                        <td>name</td>
                        <td>creation date</td>
                        <td>real hero</td>
                        <td>soundtrack</td>
                        <td>impact speed</td>
                        <td>weapon type</td>
                        <td>mood</td>
                        <td>car</td>
                        <td>coordinates</td>
                    </tr>
                </thead>
                <tbody>
                    {displayData}
                </tbody>
            </table>
        </div>
    );
}


export default TableHuman;

