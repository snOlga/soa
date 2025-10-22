import React, { useState } from "react";
import "./App.css";


function CreateHuman() {
    const [human, setHuman] = useState({
        name: "",
        coordinates: { x: 0, y: 0 },
        isRealHero: false,
        impactSpeed: 0,
        soundtrackName: "",
        weaponType: "AXE",
        mood: "SORROW",
        car: { name: "", coolness: 0 },
    });


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


    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log(human)
        await fetch("https://localhost:8080/api/humans", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(human),
        });
    };


    // enum options
    const weaponTypes = ["AXE", "KNIFE", "MACHINE_GUN", "BAT"];
    const moods = ["SORROW", "LONGING", "GLOOM", "APATHY", "FRENZY"];


    return (
        <div className="form-container">
            <h2>Create Human</h2>
            <form onSubmit={handleSubmit} className="form-fields">
                <label>
                    Name:
                    <input
                        name="name"
                        value={human.name}
                        onChange={handleChange}
                        required="true" />
                </label>


                <fieldset>
                    <legend>Coordinates</legend>
                    <label>
                        X:
                        <input
                            name="x"
                            type="number"
                            value={human.coordinates.x}
                            onChange={(e) => handleNestedChange(e, "coordinates")}
                            required="true"
                        />
                    </label>
                    <label>
                        Y:
                        <input
                            name="y"
                            type="number"
                            value={human.coordinates.y}
                            onChange={(e) => handleNestedChange(e, "coordinates")}
                            required="true"
                        />
                    </label>
                </fieldset>


                <fieldset>
                    <legend>Car</legend>
                    <label>
                        Name:
                        <input
                            name="name"
                            value={human.car.name}
                            onChange={(e) => handleNestedChange(e, "car")}
                        />
                    </label>
                    <label>
                        Coolness:
                        <input
                            name="coolness"
                            type="number"
                            value={human.car.coolness}
                            onChange={(e) => handleNestedChange(e, "car")}
                        />
                    </label>
                </fieldset>


                <label>
                    Impact speed:
                    <input
                        name="impactSpeed"
                        type="number"
                        value={human.impactSpeed}
                        onChange={handleChange}
                        required="true"
                    />
                </label>


                <label>
                    Soundtrack:
                    <input
                        name="soundtrackName"
                        value={human.soundtrackName}
                        onChange={handleChange}
                    />
                </label>


                <label>
                    Weapon:
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
                </label>


                <label>
                    Mood:
                    <select name="mood" value={human.mood} onChange={handleChange}>
                        {moods.map((m) => (
                            <option key={m} value={m}>
                                {m}
                            </option>
                        ))}
                    </select>
                </label>


                <div className="checkbox-group">
                    <label>
                        <input
                            type="checkbox"
                            name="isRealHero"
                            checked={human.isRealHero}
                            onChange={handleChange}
                        />
                        Real hero
                    </label>
                </div>


                <button type="submit">Send</button>
            </form>
        </div>
    );
}


export default CreateHuman;
