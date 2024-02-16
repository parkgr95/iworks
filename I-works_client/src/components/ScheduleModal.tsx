import { Modal } from "flowbite-react";
import { useState, useEffect } from "react";
import axios from "axios";

interface ScheduleModalProps {
  show: boolean;
  onClose: () => void;
  onSelect: (scheduleName: string, scheduleOwnerId: string, scheduleCategoryCodeId: string) => void;
}

interface OrganizationType {
  departmentName?: string;
  departmentId?: string;
  teamName?: string;
  teamId?: string;
}

const ScheduleModal: React.FC<ScheduleModalProps> = ({ show, onClose, onSelect }) => {
  // const [selectedSchedule, setSelectedSchedule] = useState<string | null>(null);
  const [departmentList, setDepartmentList] = useState<OrganizationType[]>([]);
  const [teamList, setTeamList] = useState<OrganizationType[]>([]);
  

  useEffect(() => {
    if (show) {
      axios.all([
        axios.get('https://suhyeon.site/api/address/department/all'),
        axios.get('https://suhyeon.site/api/address/team/all'),
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

  const handleSelect = (scheduleName: string, scheduleOwnerId: string, scheduleCategoryCodeId: string) => {
    // 선택한 부서를 onSelect 콜백을 통해 부모 창에 전달
    // setSelectedSchedule(scheduleName);
    onSelect(scheduleName, scheduleOwnerId, scheduleCategoryCodeId);
    onClose(); // 모달 닫기
  };

  return (
    <Modal
      show={show}
      onClose={onClose}
      size="sm"
    > 
      <div className="flex flex-col items-start py-10 mx-6 mb-6 border-2 pl-10">
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

export default ScheduleModal;