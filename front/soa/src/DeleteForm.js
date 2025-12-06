import React, { useState } from "react";
import "./App.css";


function DeleteForm() {
    const weaponTypes = ["AXE", "KNIFE", "MACHINE_GUN", "BAT"];

    const [weaponType, setWeaponType] = useState("AXE");
    const [deleteAll, setDeleteAll] = useState(false);
    const [coolness, setCoolness] = useState(0);

    const deleteByWeapon = async (e) => {
        e.preventDefault();
        await fetch("https://localhost:9018/humans/by-weapon" + (deleteAll ? "-all" : "") + "?weapon-type=" + weaponType, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" }
        });
    };

    const deleteByCoolness = async (e) => { 
        e.preventDefault();
        await fetch("https://localhost:9018/humans/less-cool-car" + "?max-coolness=" + coolness, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" }
        });
    };


    return (
        <div className="form-container">
            <h2>Delete by...</h2>
            <form className="form-fields">
                <label>
                    Weapon:
                    <select
                        name="weaponType"
                        value={weaponType}
                        onChange={(e) => setWeaponType(e.target.value)}>
                        {weaponTypes.map((w) => (
                            <option key={w} value={w}>
                                {w}
                            </option>
                        ))}
                    </select>
                </label>
                <div className="checkbox-group">
                    <label>
                        <input
                            type="checkbox"
                            name="deleteAll"
                            checked={deleteAll}
                            onChange={() => setDeleteAll(!deleteAll)}
                        />
                        Delete everyone
                    </label>
                </div>
                <button type="submit" onClick={deleteByWeapon}>Delete</button>
                <hr />
                <label>
                    Everyone, who have car with less coolness:
                    <input
                        name="coolness"
                        type="number"
                        value={coolness}
                        onChange={(e) => setCoolness(e.target.value)}
                    />
                </label>
                <button type="submit" onClick={deleteByCoolness}>Delete</button>
            </form>
        </div>
    );
}


export default DeleteForm;





