import React from 'react';
import { useState, useEffect } from 'react';
import "./App.css";


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
    const [filters, setFilters] = useState([{ field: "id", sign: "=", value: "" }]);
    const [humans, setHumans] = useState([])
    const [page, setPage] = useState(0)
    const [sortingList, setSortingList] = useState([])

    const pageSize = 20;
    const weaponTypes = ["AXE", "KNIFE", "MACHINE_GUN", "BAT"];
    const moods = ["SORROW", "LONGING", "GLOOM", "APATHY", "FRENZY"];
    const fieldsSort = [
        "id",
        "name",
        "coordinates",
        "creationDate",
        "isRealHero",
        "impactSpeed",
        "soundtrackName",
        "weaponType",
        "mood",
        "car"]
    const fieldsFilter = [
        "id",
        "name",
        "coordinates.x",
        "coordinates.y",
        "creationDate",
        "isRealHero",
        "impactSpeed",
        "soundtrackName",
        "weaponType",
        "mood",
        "car.coolness",
        "car.name"]
    const numericFields = [
        "id",
        "coordinates.x",
        "coordinates.y",
        "impactSpeed",
        "car.coolness"]
    const booleanFields = [
        "isRealHero"]
    const dateFields = [
        "creationDate"
    ]
    const signs = ["=", "<", ">", ">=", "<="];


    useEffect(() => {
        getAll();
    }, [page]);

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


    const handleChangeSorting = (e) => {
        setSortingList([...sortingList, e.target.value])
    }


    const removeField = (it) => {
        setSortingList(sortingList.filter((f, i) => i !== it));
    }


    const showAllSortingElements = sortingList.map(
        (field, it) => {
            return (
                <label>
                    <input type='text' value={field} readonly />
                    <button onClick={() => removeField(it)}>🗑️</button>
                </label>
            )
        })

    const checkFilterValue = (index, value) => {
        const newFilters = [...filters];
        const field = newFilters[index].field;

        if (field === '')
            newFilters[index] = { ...newFilters[index], value: '' };

        const num = Number(value);
        if (numericFields.includes(field) && (!Number.isInteger(num) || num <= 0))
            newFilters[index] = { ...newFilters[index], value: value.slice(0, -1) };

        if (booleanFields.includes(field) &&
            !['true', 'false', 't', 'f'].includes(value.toLowerCase()))
            newFilters[index] = { ...newFilters[index], value: value.slice(0, -1) };

        setFilters(newFilters);
    };


    const handleFilterChange = (index, key, value) => {
        const newFilters = [...filters];
        newFilters[index][key] = value;
        setFilters(newFilters);
        if (key == 'value')
            checkFilterValue(index, value)
    };

    const addFilterRow = () => {
        setFilters([...filters, { field: "id", sign: "=", value: "" }]);
    };

    const removeFilterRow = (index) => {
        setFilters(filters.filter((_, i) => i != index));
    };

    const buildFilterLine = () => {
        return filters
            .filter(f => f.field && f.value !== "")
            .map(f => `${f.field}${f.sign}${f.value}`)
            .join(";");
    };

    const getAll = () => {
        if (page < 0) {
            setPage(0)
            return
        }
        const sortingLine = sortingList.join(";");
        const filterLine = buildFilterLine();

        console.log(`https://localhost:9018/humans?from=${page}&page-size=${pageSize}&filter=${encodeURIComponent(filterLine)}&sort-by=${sortingLine}`)

        fetch(`https://localhost:9018/humans?from=${page}&page-size=${pageSize}&filter=${encodeURIComponent(filterLine)}&sort-by=${sortingLine}`, {
            method: "GET",
            headers: { "Content-Type": "application/json" }
        })
            .then(resp => resp.json())
            .then(res => setHumans(res));
    }

    const remove = (humanId) => {
        fetch("https://localhost:9018/humans/" + humanId, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" }
        })
    }

    const update = () => {
        fetch("https://localhost:9018/humans/" + editingHuman.id, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(editingHuman),
        })
        setHuman({ ...editingHuman, id: -1 })
    }

    const displayData = humans.map(
        (human) => {
            if (human.id == editingHuman.id) {
                return (
                    <tr>
                        <td>{human.id}</td>
                        <td>
                            <input
                                name="name"
                                value={editingHuman.name}
                                onChange={handleChange}
                                required="true" />
                        </td>
                        <td>{new Date(human.creationDate).toDateString()}</td>
                        <td>
                            <input
                                type="checkbox"
                                name="isRealHero"
                                checked={editingHuman.isRealHero}
                                onChange={handleChange}
                            />
                            Real hero
                        </td>
                        <td>
                            <input
                                name="soundtrackName"
                                value={editingHuman.soundtrackName}
                                onChange={handleChange}
                            />
                        </td>
                        <td>
                            <input
                                name="impactSpeed"
                                type="number"
                                value={editingHuman.impactSpeed}
                                onChange={handleChange}
                                required="true"
                            />
                        </td>
                        <td>
                            <select
                                name="weaponType"
                                value={editingHuman.weaponType}
                                onChange={handleChange}>
                                {weaponTypes.map((w) => (
                                    <option key={w} value={w}>
                                        {w}
                                    </option>
                                ))}
                            </select>
                        </td>
                        <td>
                            <select name="mood" value={editingHuman.mood} onChange={handleChange}>
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
                                            value={editingHuman.car.name}
                                            onChange={(e) => handleNestedChange(e, "car")}
                                        />
                                    </td>
                                    <td>
                                        <input
                                            name="coolness"
                                            type="number"
                                            value={editingHuman.car.coolness}
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
                                            value={editingHuman.coordinates.x}
                                            onChange={(e) => handleNestedChange(e, "coordinates")}
                                            required="true"
                                        />
                                    </td>
                                    <td>
                                        <input
                                            name="y"
                                            type="number"
                                            value={editingHuman.coordinates.y}
                                            onChange={(e) => handleNestedChange(e, "coordinates")}
                                            required="true"
                                        />
                                    </td>
                                </tr>
                            </tbody>
                        </table></td>
                        <div className='icons'>
                            <div>
                                <button onClick={() => update()}>✔️</button>
                            </div>
                            <div>
                                <button onClick={() => setHuman({ ...human, id: -1 })}>❌</button>
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
                        <td>{human.isRealHero ? "yes" : "no"}</td>
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
                                <button onClick={() => setHuman(human)}>✏️</button>
                            </div>
                            <div>
                                <button onClick={() => remove(human.id)}>🗑️</button>
                            </div>
                        </div>
                    </tr>
                )
            }
        }
    )

    return (
        <div className="form-container">
            <div className='sort-and-filter-container'>
                <h2>Sort by</h2>
                <label>
                    {showAllSortingElements}
                    <select name="sortingElement" onChange={handleChangeSorting}>
                        {fieldsSort.map((f) => (
                            <option key={f} value={f}>
                                {f}
                            </option>
                        ))}
                    </select>
                </label>

                <h2>Filter by</h2>
                <div className="filter-section">
                    {filters.map((filter, index) => (
                        <div key={index} className="filter-row">
                            <select
                                value={filter.field}
                                onChange={(e) => handleFilterChange(index, "field", e.target.value)}
                            >
                                {/* <option value="">Select field</option> */}
                                {fieldsFilter.map((field) => (
                                    <option key={field} value={field}>{field}</option>
                                ))}
                            </select>

                            <select
                                value={filter.sign}
                                onChange={(e) => handleFilterChange(index, "sign", e.target.value)}
                            >
                                {signs.map((s) => (
                                    <option key={s} value={s}>{s}</option>
                                ))}
                            </select>

                            <input
                                type="text"
                                value={filter.value}
                                onChange={(e) => handleFilterChange(index, "value", e.target.value)}
                                placeholder="Value"
                            />

                            <button type="button" onClick={() => removeFilterRow(index)}>🗑️</button>
                        </div>
                    ))}

                    <button type="button" onClick={addFilterRow}>+ Add Filter</button>
                </div>

            </div>
            <h2>Result</h2>
            <div className='get-buttons'>
                <button onClick={() => { setPage(page - 1) }}>⬅️</button>
                <button onClick={() => { setPage(page + 1) }}>➡️</button>
                <button onClick={getAll}>Get</button>
            </div>
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



