import React, { useState } from "react";
import "./App.css";

function CreateTeam() {
    const [team, setTeam] = useState({
        name: "",
        humans: [1, 2],
    });
    const [error, setError] = useState("");
    const [currentHumanId, setCurrentHumanId] = useState(""); // ← store typed value

    const handleChange = (e) => {
        const { name, value } = e.target;

        if (name === "name") {
            setTeam((prev) => ({ ...prev, name: value }));
        } else if (name === "humans") {
            setCurrentHumanId(value);
        }
    };

    const handleAddHuman = (e) => {
        if (e.key === "Enter") {
            e.preventDefault();
            const id = parseInt(currentHumanId);

            if (team.humans.includes(id))
                return setError("Human is already in team");

            setError("");

            setTeam((prev) => ({
                ...prev,
                humans: [...prev.humans, id],
            }));

            setCurrentHumanId("");
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch("https://localhost:9081/teams", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(team),
            });

            if (!response.ok) {
                const text = await response.text();
                setError(text || `Server error (${response.status})`);
                return;
            }
        } catch (err) {
            setError("Failed to send data to the server.");
        }
    };

    const removeHuman = (humanId) => {
        setTeam((prev) => ({
            ...prev,
            humans: prev.humans.filter((id) => id !== humanId),
        }));
    };

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
                        required
                    />
                </label>

                {team.humans.map((humanId) => (
                    <label key={humanId}>
                        Member:
                        <input
                            name="humans"
                            type="number"
                            value={humanId}
                            readOnly
                        />
                        <button type="button" onClick={() => removeHuman(humanId)}>
                            🗑️
                        </button>
                    </label>
                ))}

                <label>
                    Member:
                    <input
                        name="humans"
                        type="number"
                        value={currentHumanId}
                        onChange={handleChange}
                        onKeyDown={handleAddHuman}
                        placeholder="Type ID and press Enter"
                        min={0}
                    />
                </label>

                <button type="submit">Send</button>
            </form>

            {error && <p className="error-message">{error}</p>}
        </div>
    );
}

export default CreateTeam;
