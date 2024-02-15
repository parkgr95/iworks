import { Modal } from "flowbite-react";
import { useState, useEffect } from "react";
import axios from "axios";
import { getAccessToken } from "../utils/auth";

interface BoardModalProps {
  show: boolean;
  onClose: () => void;
  onSelect: (boardName: string, boardOwnerId: string, boardCategoryCodeId: string) => void;
}

interface OrganizationType {
  departmentName?: string;
  departmentId?: string;
  teamName?: string;
  teamId?: string;
}

const BoardModal: React.FC<BoardModalProps> = ({ show, onClose, onSelect }) => {
  // const [selectedBoard, setSelectedBoard] = useState<string | null>(null);
  const [departmentList, setDepartmentList] = useState<OrganizationType[]>([]);
  const [teamList, setTeamList] = useState<OrganizationType[]>([]);
  

  useEffect(() => {
    if (show) {
      axios.all([
        axios.get('https://suhyeon.site/api/address/department/all', {
          headers: {
            Authorization: 'Bearer ' + getAccessToken(),
          },
        }),
        axios.get('https://suhyeon.site/api/address/team/all', {
          headers: {
            Authorization: 'Bearer ' + getAccessToken(),
          },
        }),
      ])
        .then(axios.spread((departmentRes, teamRes) => {
          setDepartmentList(departmentRes.data.data);
          setTeamList(teamRes.data.data);
        }))
        .catch((err) => {
          console.error(err);
        });
    }
  }, [show]);

  const handleSelect = (boardName: string, boardOwnerId: string, boardCategoryCodeId: string) => {
    // 선택한 부서를 onSelect 콜백을 통해 부모 창에 전달
    // setSelectedBoard(boardName);
    onSelect(boardName, boardOwnerId, boardCategoryCodeId);
    onClose(); // 모달 닫기
  };

  return (
    <Modal
      show={show}
      onClose={onClose}
      size="sm"
    > 
      <h1 className="mt-6 mb-2 pl-8 font-semibold text-lg">게시판 선택</h1>
      <div className="flex flex-col items-start py-10 mx-6 mb-6 border-2 pl-10">

        <button className="hover:text-blue-700" onClick={() => handleSelect('공지게시판', '1', '1')}>공지게시판</button>
        <button className="hover:text-blue-700" onClick={() => handleSelect('자유게시판', '2', '2')}>자유게시판</button>
        <h2 className="pointer-events-none">부서게시판</h2>
        <ul>
          {departmentList.map((department) => (
            <li className="pl-5 hover:text-blue-700" key={department.departmentId}>
              <button onClick={() => handleSelect(department.departmentName + '게시판' || '', department.departmentId || '', '3')}>- {department.departmentName}게시판</button>
            </li>
          ))}
        </ul>
        <h2 className="pointer-events-none">그룹게시판</h2>
        <ul>
          {teamList.map((team) => (
            <li className="pl-5 hover:text-blue-700" key={team.teamId}>
              <button onClick={() => handleSelect(team.teamName + '게시판' || '', team.teamId || '', '4')}>- {team.teamName}게시판</button>
            </li>
          ))}
        </ul>
      </div>
    </Modal>
  );
};

export default BoardModal;