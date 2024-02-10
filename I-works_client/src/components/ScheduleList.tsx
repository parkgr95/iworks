interface prop {
    contents: any[]
  }
  
  function ScheduleList(props: prop) {
  
    const contents = props.contents
    
    const filteredList = contents.filter((obj, index, self) => 
      index === self.findIndex((t) => t.scheduleId === obj.scheduleId)
    )

    const sortedList = filteredList.slice().sort((a, b) => a.scheduleId - b.scheduleId)

    return (
      <>
        {sortedList.map((schedule) => {
          return (
            <div className="flex my-4 gap-4 border-black border-2 rounded-xl p-4" key={schedule.scheduleId}>
              <div>{schedule.scheduleTitle}</div>
              <div>{schedule.scheduleStartDate.slice(0,-9)}</div>
              <div>{schedule.scheduleEndDate.slice(0,-9)}</div>
            </div>
          )
        })}
      </>
    )
  }
  
  export default ScheduleList
  