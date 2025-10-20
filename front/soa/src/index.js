import React from 'react';
import ReactDOM from 'react-dom/client';
import CreateHuman from './CreateHuman';
import "./index.css";
import TableHuman from './TableHuman';
import CreateTeam from './CreateTeam';
import TableTeam from './TableTeam';


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
 // <React.StrictMode>
 <div className="main-container">
   <div className="container">
     <CreateHuman />
     <TableHuman />
   </div>
   <div className="container">
     <CreateTeam />
     <TableTeam />
   </div>
 </div>
 // </React.StrictMode>
);