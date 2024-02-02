// function BoardModal({ isOpen, onClose, onSelectBoard, boards }) {

//   if (!isOpen) {
//     return null
//   }

//   return (
//     <div className="modal">
//       <div className="modal-content">
//         <span className="close" onClick={onClose}>&times;</span>
//         <h2>Select a Board</h2>
//         <select onChange={(e) => onSelectBoard(e.target.value)}>
//           {boards.map(board => (
//             <option key={board.id} value={board.id}>{board.name}</option>
//           ))}
//         </select>
//       </div>
//     </div>
//   )
// }

// export default BoardModal


// import React, { useState, useEffect } from 'react';
// import Modal from './Modal';

// const ParentComponent = () => {
//   const [isModalOpen, setModalOpen] = useState(false);
//   const [selectedBoard, setSelectedBoard] = useState(null);
//   const [boards, setBoards] = useState([]);

//   useEffect(() => {
//     // API 호출 함수 정의
//     const fetchBoards = async () => {
//       try {
//         const response = await fetch('YOUR_API_ENDPOINT');
//         const data = await response.json();
//         setBoards(data); // API에서 받아온 데이터로 boards 상태 업데이트
//       } catch (error) {
//         console.error('Error fetching boards:', error);
//       }
//     };

//     // 컴포넌트 마운트 시 API 호출
//     fetchBoards();
//   }, []); // 빈 배열을 전달하여 한 번만 호출되도록 설정

//   const openModal = () => {
//     setModalOpen(true);
//   };

//   const closeModal = () => {
//     setModalOpen(false);
//   };

//   const handleSelectBoard = (boardId) => {
//     // Handle the selected board as needed
//     setSelectedBoard(boardId);
//     closeModal();
//   };

//   return (
//     <div>
//       <button onClick={openModal}>Select Board</button>
//       <Modal
//         isOpen={isModalOpen}
//         onClose={closeModal}
//         onSelectBoard={handleSelectBoard}
//         boards={boards}
//       />
//       {selectedBoard && <p>Selected Board: {selectedBoard}</p>}
//     </div>
//   );
// };

// export default ParentComponent;